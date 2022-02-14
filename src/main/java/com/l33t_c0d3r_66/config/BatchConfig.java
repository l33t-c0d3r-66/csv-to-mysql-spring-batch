package com.l33t_c0d3r_66.config;

import com.l33t_c0d3r_66.model.Sales;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Sales> reader() {
        FlatFileItemReader<Sales> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("10000sales.csv"));
        flatFileItemReader.setLineMapper(getLineMapper());
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    public LineMapper<Sales> getLineMapper() {
        DefaultLineMapper<Sales> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        //Set Names of the Columns
        delimitedLineTokenizer.setNames(new String[]{"Region","Country","Item Type","Unit Sold","Unit Price","Unit Cost"});
        //Set Index of the columns from which to get data.
        delimitedLineTokenizer.setIncludedFields(new int[]{0,1,2,8,9,10});
        BeanWrapperFieldSetMapper<Sales> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Sales.class);
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return defaultLineMapper;
    }

    @Bean
    public SalesItemProcessor processor() {
        return new SalesItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Sales> writer() {
        JdbcBatchItemWriter<Sales> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        jdbcBatchItemWriter.setSql("insert into sales(region, country, itemType, unitSold, unitCost,unitPrice) values (:region, :country, :itemType, :unitSold, :unitCost, :unitPrice)");
        jdbcBatchItemWriter.setDataSource(this.dataSource);
        return jdbcBatchItemWriter;
    }

    @Bean
    public Job importSalesJob() {
        return this.jobBuilderFactory.get("SALES_IMPORT_JOB")
                .incrementer(new RunIdIncrementer()).flow(step1()).end().build();
    }

    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .<Sales, Sales>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer()).build();
    }
}

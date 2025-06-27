package com.kodilla.csv_conventer_2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    FlatFileItemReader<PersonInput> reader() {
        FlatFileItemReader<PersonInput> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("users_utf8.csv"));
        reader.setEncoding("UTF-8");

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames("firstName", "lastName", "birthDate");

        BeanWrapperFieldSetMapper<PersonInput> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(PersonInput.class);

        // String -> LocalDate
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(String.class, java.time.LocalDate.class,
                s -> java.time.LocalDate.parse(s, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
        mapper.setConversionService(conversionService);

        DefaultLineMapper<PersonInput> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    PersonProcessor processor() {
        return new PersonProcessor();
    }

    @Bean
    FlatFileItemWriter<PersonOutput> writer() {
        BeanWrapperFieldExtractor<PersonOutput> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"firstName", "lastName", "age"});

        DelimitedLineAggregator<PersonOutput> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(extractor);

        FlatFileItemWriter<PersonOutput> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("output.csv"));
        writer.setShouldDeleteIfExists(true);
        writer.setLineAggregator(aggregator);

        return writer;
    }

    @Bean
    Step countAge(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                     ItemReader<PersonInput> reader, ItemProcessor<PersonInput, PersonOutput> processor, ItemWriter<PersonOutput> writer) {

        return new StepBuilder("countAge", jobRepository)
                .<PersonInput, PersonOutput> chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    Job countAgeJob(JobRepository jobRepository, Step countAge) {
        return new JobBuilder("countAge", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(countAge)
                .end()
                .build();
    }
}

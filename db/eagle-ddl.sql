
    drop table Activity cascade constraints;

    drop table BEHAVIORAL_ASSESSMENT cascade constraints;

    drop table CYTOBAND_POSITION cascade constraints;

    drop table ClinicalFinding cascade constraints;

    drop table DIETARY_CONSUMPTION cascade constraints;

    drop table DNA_SPECIMEN cascade constraints;

    drop table ENV_FACTOR cascade constraints;

    drop table EPI_STUDY_PARTICIPANT cascade constraints;

    drop table GENE_ALIAS cascade constraints;

    drop table GENE_DIM cascade constraints;

    drop table GENE_EXPR_REPORTER cascade constraints;

    drop table GENE_SNP_ASSO cascade constraints;

    drop table GENOTYPE_FACT cascade constraints;

    drop table LIFESTYLE cascade constraints;

    drop table MEDICAL_CONDITION cascade constraints;

    drop table RELATIVE cascade constraints;

    drop table SNP_ANALYSIS_FINDING_FACT cascade constraints;

    drop table SNP_ANALYSIS_GROUP cascade constraints;

    drop table SNP_ASSAY cascade constraints;

    drop table SNP_ASSOCIATION_ANALYSIS cascade constraints;

    drop table SNP_DIM cascade constraints;

    drop table SNP_FREQUENCY_FACT cascade constraints;

    drop table SNP_PANEL cascade constraints;

    drop table SPECIMEN cascade constraints;

    drop table STDPT_ANALYSIS_GRP_AS cascade constraints;

    drop table STUDY_DIM cascade constraints;

    drop table STUDY_PANEL_ASSO cascade constraints;

    drop table STUDY_PARTICIPANT cascade constraints;

    drop table STUDY_POPULATION cascade constraints;

    drop table STUDY_STDPOPUPLATION_ASSO cascade constraints;

    drop table STUDY_TIMECOURSE_DIM cascade constraints;

    drop table TOBACCO_CONSUMPTION cascade constraints;

    drop sequence hibernate_sequence;

    create table Activity (
        ADMIN_ID varchar2(255) not null,
        ACTIVITY_DESC varchar2(255),
        ACTIVITY_NAME varchar2(255),
        DAYS_START number(19,0),
        DAYS_STOP number(19,0),
        STATUS varchar2(255),
        PARTICIPANT_ID number(19,0),
        TIMECOURSE_ID number(19,0),
        PATIENT_DID number(19,0),
        primary key (ADMIN_ID)
    );

    create table BEHAVIORAL_ASSESSMENT (
        ID number(19,0) not null,
        DEPRESSION_SCORE number(10,0),
        ANXIETY_SCORE number(10,0),
        FAGERSTROM_SCORE number(10,0),
        primary key (ID)
    );

    create table CYTOBAND_POSITION (
        CYTOBAND_POSITION_ID number(19,0) not null,
        CHROMOSOME varchar2(255),
        CYTOBAND varchar2(255),
        CB_START number(19,0),
        CB_END_POS number(19,0),
        ORGANISM varchar2(255),
        primary key (CYTOBAND_POSITION_ID)
    );

    create table ClinicalFinding (
        CLINICAL_FINDING_ID number(19,0) not null,
        PARTICIPANT_ID number(19,0),
        PARTICIPANT_DID number(19,0),
        primary key (CLINICAL_FINDING_ID)
    );

    create table DIETARY_CONSUMPTION (
        ID number(19,0) not null,
        FREQUENCY varchar2(255),
        NAME varchar2(255),
        PARTICIPANT_ID number(19,0),
        primary key (ID)
    );

    create table DNA_SPECIMEN (
        SPECIMEN_ID varchar2(255) not null,
        DNA_AMPLICATION_METHOD varchar2(255),
        DNA_EXTRACTION_METHOD varchar2(255),
        DNA_MATERIAL_TYPE varchar2(255),
        primary key (SPECIMEN_ID)
    );

    create table ENV_FACTOR (
        ID number(19,0) not null,
        EXPOSURE_LEVEL varchar2(255),
        EXPOSURE_AREA varchar2(255),
        EXPOSURE_AGE_GROUP varchar2(255),
        EXPOSURE_TYPE varchar2(255),
        PARTICIPANT_ID number(19,0),
        primary key (ID)
    );

    create table EPI_STUDY_PARTICIPANT (
        ID number(19,0) not null,
        CASE_CONTROL_STATUS varchar2(255),
        GENDER varchar2(255),
        AGE_AT_DIAGNOSIS double precision,
        AGE_AT_DIAGNOSIS_MIN double precision,
        AGE_AT_DIAGNOSIS_MAX double precision,
        AGE_AT_DEATH double precision,
        AGE_AT_DEATH_MIN double precision,
        AGE_AT_DEATH_MAX double precision,
        AGE_AT_ENROLL double precision,
        AGE_AT_ENROLL_MIN double precision,
        AGE_AT_ENROLL_MAX double precision,
        DAYS_ON_STUDY number(10,0),
        ETHNIC_GROUP_CODE varchar2(255),
        FAMILY_HISTORY varchar2(255),
        INSTITUTION_NAME varchar2(255),
        DAYS_OFF_STUDY number(10,0),
        RACE_CODE varchar2(255),
        PARTICIPANT_DID varchar2(255),
        SURVIVAL_STATUS varchar2(255),
        OFF_STUDY char(1),
        STUDY_NAME varchar2(255),
        POPULATION_ID number(19,0),
        ASSESSMENT_ID number(19,0) not null unique,
        LIFESTYLE_ID number(19,0) not null unique,
        primary key (ID)
    );

    create table GENE_ALIAS (
        ALIAS_ID number(19,0) not null,
        ALIAS varchar2(255),
        GENE_SYMBOL varchar2(255),
        GENE_ID number(19,0),
        primary key (ALIAS_ID)
    );

    create table GENE_DIM (
        GENE_ID number(19,0) not null,
        CHROMOSOME varchar2(255),
        FEATURE_NAME varchar2(255),
        CHROMOSOME_START number(19,0),
        CHROMOSOME_END number(19,0),
        primary key (GENE_ID)
    );

    create table GENE_EXPR_REPORTER (
        REPORTER_ID number(19,0) not null,
        REPORTER_NAME varchar2(255),
        REPORTER_TYPE varchar2(255),
        REPORTER_PLATFORM varchar2(255),
        GENE_ID number(19,0),
        primary key (REPORTER_ID)
    );

    create table GENE_SNP_ASSO (
        SNPANNO_ID number(19,0) not null,
        GENE_ID number(19,0) not null,
        primary key (GENE_ID, SNPANNO_ID)
    );

    create table GENOTYPE_FACT (
        GS_ID number(19,0) not null,
        ALLELE1 varchar2(255),
        ALLELE2 varchar2(255),
        NORMAL_X float,
        NORMAL_Y float,
        QUALITY_SCORE float,
        RAW_X float,
        RAW_Y float,
        STATUS varchar2(255),
        SNPANNO_ID number(19,0),
        STUDY_NAME varchar2(255) not null,
        SPECIMEN_ID varchar2(255) not null,
        primary key (GS_ID)
    );

    create table LIFESTYLE (
        ID number(19,0) not null,
        EDUCATION_LEVEL varchar2(255),
        ECONOMIC_STATUS varchar2(255),
        RESIDENTIAL_AREA varchar2(255),
        MARITAL_STATUS varchar2(255),
        RELIGION varchar2(255),
        primary key (ID)
    );

    create table MEDICAL_CONDITION (
        ID number(19,0) not null,
        CONDITION_NAME varchar2(255),
        RELATIVE_ID number(19,0),
        primary key (ID)
    );

    create table RELATIVE (
        ID number(19,0) not null,
        RELATIONSHIP_TYPE varchar2(255),
        ALIVE number(1,0),
        LIVING_COMPANION number(1,0),
        PARTICIPANT_ID number(19,0),
        primary key (ID)
    );

    create table SNP_ANALYSIS_FINDING_FACT (
        ASSO_ANA_FACT_ID number(19,0) not null,
        ASSO_ANA_PVALUE float,
        ASSO_ANA_RANK number(10,0),
        OR_AGGRESSIVE_HETEROZYGOTE double precision,
        OR_AGGRESSIVE_HOMOZYGOTE double precision,
        OR_NONAGGRESSIVE_HETEROZYGOTE double precision,
        OR_NONAGGRESSIVE_HOMOZYGOTE double precision,
        OR_HETEROZYGOTE double precision,
        OR_HOMOZYGOTE double precision,
        SNPANNO_ID number(19,0),
        ASSO_ANALYSIS_ID number(19,0),
        STUDY_NAME varchar2(255) not null,
        primary key (ASSO_ANA_FACT_ID)
    );

    create table SNP_ANALYSIS_GROUP (
        ANA_GRP_ID number(19,0) not null,
        MEMBER_COUNT number(10,0),
        ANA_GRP_NAME varchar2(255),
        ANA_GRP_DESCRIPTION varchar2(255),
        ASSO_ANALYSIS_ID number(19,0),
        primary key (ANA_GRP_ID)
    );

    create table SNP_ASSAY (
        ASSAY_ID number(19,0) not null,
        DESIGN_ALLELES varchar2(255),
        DESIGN_SCORE float,
        DESIGN_SEQUENCE varchar2(255),
        DESIGN_STRAND varchar2(255),
        STATUS varchar2(255),
        VENDOR_ASSAY_ID varchar2(255),
        VERSION varchar2(255),
        SNP_PANEL_ID number(19,0) not null,
        SNPANNO_ID number(19,0) not null,
        primary key (SNP_PANEL_ID, SNPANNO_ID)
    );

    create table SNP_ASSOCIATION_ANALYSIS (
        ASSO_ANALYSIS_ID number(19,0) not null,
        ASSO_ANALYSIS_NAME varchar2(255),
        ASSO_ANALYSIS_DESCRIPTION varchar2(255),
        ASSO_ANALYSIS_METHODS varchar2(255),
        STUDY_NAME varchar2(255) not null,
        primary key (ASSO_ANALYSIS_ID)
    );

    create table SNP_DIM (
        SNPANNO_ID number(19,0) not null,
        PHYSICAL_LOCATION number(19,0),
        CHROMOSOME varchar2(255),
        DBSNP_BUILD varchar2(255),
        DBSNPID varchar2(255),
        GENOME_BUILD varchar2(255),
        primary key (SNPANNO_ID)
    );

    create table SNP_FREQUENCY_FACT (
        SNP_FREQ_ID number(19,0) not null,
        COMPLETION_RATE double precision,
        HARDYWEINBERG_P_VALUE float,
        HETEROZYGOTE_COUNT number(10,0),
        MINOR_ALLELE_FREQ float,
        MISSING_ALLELE_COUNT number(10,0),
        MISSSING_GENOTYPE_COUNT number(10,0),
        OTHER_ALLELE varchar2(255),
        OTHER_ALLELE_COUNT number(10,0),
        OTHER_HOMOZYGOTE_COUNT number(10,0),
        REFERENCE_ALLELE varchar2(255),
        REFERENCE_ALLELE_COUNT number(10,0),
        REFERENCE_HOMOZYGOTE_COUNT number(10,0),
        SNPANNO_ID number(19,0),
        POPULATION_ID number(19,0),
        STUDY_NAME varchar2(255) not null,
        primary key (SNP_FREQ_ID)
    );

    create table SNP_PANEL (
        SNP_PANEL_ID number(19,0) not null,
        ASSAY_COUNT number(10,0),
        PANEL_DESCRIPTION varchar2(255),
        SNP_PANEL_NAME varchar2(255),
        TECHNOLOGY varchar2(255),
        VENDOR varchar2(255),
        VENDOR_PANEL_ID varchar2(255),
        VERSION varchar2(255),
        primary key (SNP_PANEL_ID)
    );

    create table SPECIMEN (
        SPECIMEN_ID varchar2(255) not null,
        SPECIMEN_TYPE varchar2(255),
        COLLECTION_METHOD varchar2(255),
        TIMECOURSE_NAME varchar2(255),
        PARTICIPANT_DID varchar2(255),
        PARTICIPANT_ID number(19,0),
        primary key (SPECIMEN_ID)
    );

    create table STDPT_ANALYSIS_GRP_AS (
        PARTICIPANT_DID number(19,0) not null,
        ANA_GRP_ID number(19,0) not null,
        primary key (ANA_GRP_ID, PARTICIPANT_DID)
    );

    create table STUDY_DIM (
        STUDY_NAME varchar2(255) not null,
        STUDY_DESCRIPTION varchar2(255),
        END_DATE date,
        STUDY_SPONSOR varchar2(255),
        START_DATE date,
        primary key (STUDY_NAME)
    );

    create table STUDY_PANEL_ASSO (
        STUDY_NAME varchar2(255) not null,
        SNP_PANEL_ID number(19,0) not null,
        primary key (STUDY_NAME, SNP_PANEL_ID)
    );


    create table STUDY_POPULATION (
        POPULATION_ID number(19,0) not null,
        MEMBER_COUNT number(10,0),
        POPULATION_NAME varchar2(255),
        POPULATION_DESC varchar2(255),
        SOURCE varchar2(255),
        primary key (POPULATION_ID)
    );

    create table STUDY_STDPOPUPLATION_ASSO (
        STUDY_NAME varchar2(255) not null,
        POPULATION_ID number(19,0) not null,
        primary key (POPULATION_ID, STUDY_NAME)
    );

    create table STUDY_TIMECOURSE_DIM (
        TIMECOURSE_ID number(19,0) not null,
        TIMECOURSE_NAME varchar2(255),
        primary key (TIMECOURSE_ID)
    );

    create table TOBACCO_CONSUMPTION (
        ID number(19,0) not null,
        TOBACCO_TYPE varchar2(255),
        SMOKING_STATUS varchar2(255),
        INTENSITY number(10,0),
        DURATION number(10,0),
        AGE_AT_INITIATION number(10,0),
        YEARS_SINCE_QUITTING number(10,0),
        PARTICIPANT_ID number(19,0),
        RELATIVE_ID number(19,0),
        primary key (ID)
    );

    alter table Activity 
        add constraint FKA126572F33E34BBC 
        foreign key (PATIENT_DID) 
        references EPI_STUDY_PARTICIPANT;

    alter table Activity 
        add constraint FKA126572F4C628067 
        foreign key (TIMECOURSE_ID) 
        references STUDY_TIMECOURSE_DIM;

    alter table Activity 
        add constraint FKA126572FA1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table Activity 
        add constraint FKA126572F1E6A091E 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table ClinicalFinding 
        add constraint FKAB8FD8D6A1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table ClinicalFinding 
        add constraint FKAB8FD8D646B388EA 
        foreign key (PARTICIPANT_DID) 
        references EPI_STUDY_PARTICIPANT;

    alter table DIETARY_CONSUMPTION 
        add constraint FKAE80CD90A1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table DNA_SPECIMEN 
        add constraint FK246E41506ED3C787 
        foreign key (SPECIMEN_ID) 
        references SPECIMEN;

    alter table ENV_FACTOR 
        add constraint FK6E14D01A1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table EPI_STUDY_PARTICIPANT 
        add constraint FKD3C4B47CA9739755 
        foreign key (LIFESTYLE_ID) 
        references LIFESTYLE;

    alter table EPI_STUDY_PARTICIPANT 
        add constraint FKD3C4B47C7B13A627 
        foreign key (POPULATION_ID) 
        references STUDY_POPULATION;

    alter table EPI_STUDY_PARTICIPANT 
        add constraint FKD3C4B47CE7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table EPI_STUDY_PARTICIPANT 
        add constraint FKD3C4B47C68E6B7BC 
        foreign key (ASSESSMENT_ID) 
        references BEHAVIORAL_ASSESSMENT;

    alter table GENE_ALIAS 
        add constraint FK454DF08616D3B9FA 
        foreign key (GENE_ID) 
        references GENE_DIM;

    alter table GENE_EXPR_REPORTER 
        add constraint FKD618544116D3B9FA 
        foreign key (GENE_ID) 
        references GENE_DIM;

    alter table GENE_SNP_ASSO 
        add constraint FKCE1C58A2B475F329 
        foreign key (SNPANNO_ID) 
        references SNP_DIM;

    alter table GENE_SNP_ASSO 
        add constraint FKCE1C58A216D3B9FA 
        foreign key (GENE_ID) 
        references GENE_DIM;

    alter table GENOTYPE_FACT 
        add constraint FKC05A3412B475F329 
        foreign key (SNPANNO_ID) 
        references SNP_DIM;

    alter table GENOTYPE_FACT 
        add constraint FKC05A34126ED3C787 
        foreign key (SPECIMEN_ID) 
        references SPECIMEN;

    alter table GENOTYPE_FACT 
        add constraint FKC05A3412E7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table MEDICAL_CONDITION 
        add constraint FK43C84AD5BAC3D1F 
        foreign key (RELATIVE_ID) 
        references RELATIVE;

    alter table RELATIVE 
        add constraint FKD99EACCA1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table SNP_ANALYSIS_FINDING_FACT 
        add constraint FK3FA40A9B146A6274 
        foreign key (ASSO_ANALYSIS_ID) 
        references SNP_ASSOCIATION_ANALYSIS;

    alter table SNP_ANALYSIS_FINDING_FACT 
        add constraint FK3FA40A9BB475F329 
        foreign key (SNPANNO_ID) 
        references SNP_DIM;

    alter table SNP_ANALYSIS_FINDING_FACT 
        add constraint FK3FA40A9BE7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table SNP_ANALYSIS_GROUP 
        add constraint FKBE0AA766146A6274 
        foreign key (ASSO_ANALYSIS_ID) 
        references SNP_ASSOCIATION_ANALYSIS;

    alter table SNP_ASSAY 
        add constraint FKE69582EF668B8221 
        foreign key (SNP_PANEL_ID) 
        references SNP_PANEL;

    alter table SNP_ASSAY 
        add constraint FKE69582EFB475F329 
        foreign key (SNPANNO_ID) 
        references SNP_DIM;

    alter table SNP_ASSOCIATION_ANALYSIS 
        add constraint FKF2DC31A4E7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table SNP_FREQUENCY_FACT 
        add constraint FKABD78FB97B13A627 
        foreign key (POPULATION_ID) 
        references STUDY_POPULATION;

    alter table SNP_FREQUENCY_FACT 
        add constraint FKABD78FB9B475F329 
        foreign key (SNPANNO_ID) 
        references SNP_DIM;

    alter table SNP_FREQUENCY_FACT 
        add constraint FKABD78FB9E7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table SPECIMEN 
        add constraint FKAF84F308A1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    alter table SPECIMEN 
        add constraint FKAF84F30846B388EA 
        foreign key (PARTICIPANT_DID) 
        references EPI_STUDY_PARTICIPANT;

    alter table STDPT_ANALYSIS_GRP_AS 
        add constraint FKD8CC4FB7C0A5F34D 
        foreign key (ANA_GRP_ID) 
        references SNP_ANALYSIS_GROUP;

    alter table STDPT_ANALYSIS_GRP_AS 
        add constraint FKD8CC4FB746B388EA 
        foreign key (PARTICIPANT_DID) 
        references EPI_STUDY_PARTICIPANT;

    alter table STDPT_ANALYSIS_GRP_AS 
        add constraint FKD8CC4FB73266BA6D 
        foreign key (PARTICIPANT_DID) 
        references EPI_STUDY_PARTICIPANT;

    alter table STUDY_DIM 
        add constraint FK31A050D2E7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table STUDY_PANEL_ASSO 
        add constraint FK51634FDF668B8221 
        foreign key (SNP_PANEL_ID) 
        references SNP_PANEL;

    alter table STUDY_PANEL_ASSO 
        add constraint FK51634FDFE7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table STUDY_PARTICIPANT 
        add constraint FK743EC17D7B13A627 
        foreign key (POPULATION_ID) 
        references STUDY_POPULATION;

    alter table STUDY_PARTICIPANT 
        add constraint FK743EC17DE7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table STUDY_STDPOPUPLATION_ASSO 
        add constraint FK9FF3DAE57B13A627 
        foreign key (POPULATION_ID) 
        references STUDY_POPULATION;

    alter table STUDY_STDPOPUPLATION_ASSO 
        add constraint FK9FF3DAE5E7EF915D 
        foreign key (STUDY_NAME) 
        references STUDY_DIM;

    alter table TOBACCO_CONSUMPTION 
        add constraint FK9B5AE7F15BAC3D1F 
        foreign key (RELATIVE_ID) 
        references RELATIVE;

    alter table TOBACCO_CONSUMPTION 
        add constraint FK9B5AE7F1A1D3AA1 
        foreign key (PARTICIPANT_ID) 
        references EPI_STUDY_PARTICIPANT;

    create sequence hibernate_sequence;

#!/bin/bash

#awslocal --endpoint-url=http://localhost:4576 sqs create-queue --queue-name test_queue --region us-east-1
#awslocal --endpoint-url=http://localhost:4576 sqs list-queues --region us-east-1

awslocal s3 mb s3://sivalabs-practice
echo "========= List of buckets ============"
awslocal s3 ls

awslocal sqs create-queue --queue-name testQueue
echo "========= List of SQS Queues ============"
awslocal sqs list-queues

awslocal sns create-topic --name testTopic
echo "========= List of SNS Topics ============"
awslocal sns list-topics

awslocal secretsmanager create-secret --name /secrets/api-secrets --secret-string '{"api-key": "secret12345", "anotherSecret": "shhhh"}'
echo "========= List of secrets ============"
awslocal secretsmanager list-secrets
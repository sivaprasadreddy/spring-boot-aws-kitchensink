#!/bin/bash

#awslocal --endpoint-url=http://localhost:4576 sqs create-queue --queue-name test_queue --region us-east-1
#awslocal --endpoint-url=http://localhost:4576 sqs list-queues --region us-east-1

awslocal s3 mb s3://sivalabs-practice
echo "========= List of buckets ============"
awslocal s3 ls

awslocal sqs create-queue --queue-name test_queue
echo "========= List of SQS Queues ============"
awslocal sqs list-queues

awslocal sns create-topic --name test_topic
echo "========= List of SNS Topics ============"
awslocal sns list-topics
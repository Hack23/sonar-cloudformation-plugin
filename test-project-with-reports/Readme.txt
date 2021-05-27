Generate example reports
cfn_nag_scan --input-path src/main/config/ -o json -> target/cfn-nag-scan.nagscan
checkov -f src/main/config/aws-cross-account-manager-master.yml -o json > target/aws-cross-account-manager-master.checkov-report
checkov -f src/main/config/azuredeploy.json -o json > target/azuredeploy.checkov-report
checkov -f src/main/config/cia-dist-cloudformation.json -o json > target/cia-dist-cloudformation.checkov-report
checkov -f src/main/config/CloudTrailAllAccounts.yml -o json > target/CloudTrailAllAccounts.checkov-report
checkov -f src/main/config/main.tf -o json > target/main.checkov-report

Generate example reports
cfn_nag_scan --input-path src/main/config/ -o json -> target/cfn-nag-scan.nagscan
checkov -d src/main/config/ -o json > target/template.checkov-report
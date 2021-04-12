checkov -v
2.0.35

~/git/sonar-cloudformation-plugin$ checkov -f src/test/resources/checkov/cia-dist-cloudformation.json -o json > src/test/resources/checkov/cia-dist-cloudformation.checkov-report

~/git/sonar-cloudformation-plugin$ checkov -l > src/test/resources/checkov/rules.txt
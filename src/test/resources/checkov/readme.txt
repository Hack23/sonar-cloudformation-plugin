sudo pip3 install -U checkov
sudo gem install cfn-nag


checkov -v
2.0.707

~/git/sonar-cloudformation-plugin$ checkov -f src/test/resources/checkov/cia-dist-cloudformation.json -o json > src/test/resources/checkov/cia-dist-cloudformation.checkov-report
~/git/sonar-cloudformation-plugin$ checkov -f src/test/resources/checkov/azuredeploy.json -o json > src/test/resources/checkov/azuredeploy.checkov-report
~/git/sonar-cloudformation-plugin$ checkov -f src/test/resources/checkov/main.tf -o json > src/test/resources/checkov/main.checkov-report


~/git/sonar-cloudformation-plugin$ checkov -l > src/test/resources/checkov/rules.txt

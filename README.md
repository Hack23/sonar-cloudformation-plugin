# sonar-cloudformation-plugin
Cloudformation template rules (cfn-nag (https://github.com/stelligent/cfn_nag) and checkov (https://github.com/bridgecrewio/checkov) add support Cloudformation and Terraform.

[![license](https://img.shields.io/github/license/Hack23/sonar-cloudformation-plugin.svg)](https://github.com/Hack23/sonar-cloudformation-plugin/raw/master/LICENSE.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.hack23.sonar/sonar-cloudformation-plugin.svg)](http://mvnrepository.com/artifact/com.hack23.sonar/sonar-cloudformation-plugin)
[![CII Best Practices](https://bestpractices.coreinfrastructure.org/projects/4545/badge)](https://bestpractices.coreinfrastructure.org/projects/4545)
[![OpenSSF Scorecard](https://api.securityscorecards.dev/projects/github.com/Hack23/sonar-cloudformation-plugin/badge)](https://api.securityscorecards.dev/projects/github.com/Hack23/sonar-cloudformation-plugin)


# Runtime

[![JDK-8](https://img.shields.io/badge/jdk-8-green.svg)]
[![JDK-11](https://img.shields.io/badge/jdk-11-green.svg)]
[![JDK-17 or higher](https://img.shields.io/badge/jdk-17-green.svg)]


## Compatibility

This plugin is compatible:

 * 1.7.3(EOL) versions with SonarQube >= 7.6 and <= 8.9.x. (Defines cloudformation language only supports cfn-nag)
 * 2.1.8(EOL) versions with SonarQube >= 7.9 and <= 8.9.x. (Requires json or/and yaml plugin supports cfn-nag/checkov)
 * 3.x version with SonarQube >= 9.2 (Uses built in support for terraform/cloudformation supports cfn-nag/checkov)


# Configuration of Quality profiles

The Cloudformation/Terraform rules can be added as a Quality profile to your sonar instance.

https://github.com/Hack23/sonar-cloudformation-plugin/releases

To install the plugin/profile, login to your sonar instance and download the jar from the releases page into the location below, making sure to remove any previous versions, once the plugin has downloaded, restart your sonar server to activate it.

```$SONARQUBE_HOME/extensions/plugins/.```


Example of how to do this with a demo docker instance:

```
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
docker exec -it sonarqube /bin/bash
cd /opt/sonarqube/extensions/plugins
wget https://search.maven.org/remotecontent?filepath=com/hack23/sonar/sonar-cloudformation-plugin/3.0.10/sonar-cloudformation-plugin-3.0.10.jar --no-check-certificate

Exit the docker instance

docker restart sonarqube
```
Access your dev sonarqube at http://localhost:9000 and you should be able to see the installed profiles


# Howto


# Cfn-nag reports 

Prepare cfn_nag reports running 
```
cfn_nag --output-format=json src/main/config/template.yml > target/template.yml.nag
```
and set the property 
```
sonar.cfn.nag.reportFiles=target/template.yml.nag (comma separated if multiple reports)
```

Or scan directories using cfn_nag_scan running 
```
cfn_nag_scan  --input-path src/main/config/ -o json -> target/cfn-nag-scan.nagscan
```
and set the property 

```
sonar.cfn.nag.reportFiles=target/cfn-nag-scan.nagscan
```

# Properties supported

```
sonar.cfn.nag.reportFiles=target/template.yml.nag,target/cfn-nag-scan.nagscan
````

One or multiple .nag or .nagscan files, note for .nag files the filename should be template filename appended with .nag and for nag_scan any filename with .nagscan suffix.


# Custom cfn-nag rules or rules not yet defined 

Will be mapped to "Custom cfn-nag failure rule or rule missing integration in this plugin." alt Custom cfn-nag warning rule or rule missing integration in this plugin.
Assumes all failures start with uppercase F and all warnings with uppercase W.


# Checkov reports 

Prepare checkov reports by running, in this example we are scanning a single file 'template.yml'

```
checkov -f template.yml -o json --output-file-path template.checkov-report
```

and set the property when scanning with sonar to the checkov output

```
sonar.checkov.reportFiles=template.checkov-report
```` 


# Properties supported

```
sonar.checkov.reportFiles=template.checkov-report
```

One or multiple checkov report files, note for .checkov-report files the filename should be template filename appended with checkov-report.


# Custom checkov rules or rules not yet defined 

Will be mapped to "Custom checkov failure rule or rule missing integration in this plugin." alt Custom checkov warning rule or rule missing integration in this plugin.


# Group the rules to CWE

CWE-311 - Missing Encryption of Sensitive Data https://cwe.mitre.org/data/definitions/311

CWE-326 - Inadequate Encryption Strength https://cwe.mitre.org/data/definitions/326

CWE-732 - Incorrect Permission Assignment for Critical Resource https://cwe.mitre.org/data/definitions/732

CWE-257 - Storing Passwords in a Recoverable Format https://cwe.mitre.org/data/definitions/257

CWE-778 - Insufficient Logging https://cwe.mitre.org/data/definitions/778

CWE-272 - Least Privilege Violation https://cwe.mitre.org/data/definitions/272

CWE-286 - Incorrect User Management https://cwe.mitre.org/data/definitions/286

CWE-770 - Allocation of Resources Without Limits or Throttling https://cwe.mitre.org/data/definitions/770

CWE-779 - Logging of Excessive Data https://cwe.mitre.org/data/definitions/779.html

CWE-200 - Exposure of Sensitive Information to an Unauthorized Actor https://cwe.mitre.org/data/definitions/200.htm 

No CWE associated

# Group the rules to NIST 800-53

800-53-AC-4 AC-4 INFORMATION FLOW ENFORCEMENT https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=AC-4

800-53-AC-6 AC-6 LEAST PRIVILEGE https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=AC-6

800-53-AU-12 AU-12 AUDIT GENERATION https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=AU-12

800-53-IA-5 IA-5 AUTHENTICATOR MANAGEMENT https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=IA-5

800-53-SC-5 SC-5 DENIAL OF SERVICE PROTECTION https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=SC-5

800-53-SC-7 SC-7 BOUNDARY PROTECTION https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=SC-7

800-53-SC-8 SC-8 TRANSMISSION CONFIDENTIALITY AND INTEGRITY https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=SC-8

800-53-SC-12 SC-12 CRYPTOGRAPHIC KEY ESTABLISHMENT AND MANAGEMENT https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=SC-12

800-53-SC-13 SC-13 CRYPTOGRAPHIC PROTECTION https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=SC-13

800-53-CP-9 CP-9 INFORMATION SYSTEM BACKUP https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=CP-9

800-53-RA-5 RA-5 VULNERABILITY SCANNING https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=RA-5

800-53-AU-11 AU-11 AUDIT RECORD RETENTION https://csrc.nist.gov/Projects/risk-management/sp800-53-controls/release-search#/control?version=5.1&number=AU-11


# Group the rules to ISO 270001

https://www.isms.online/iso-27001/annex-a-9-access-control/

A.9 Access Control A.9.2 User Access Management

A.9 Access Control A.9.3 User Responsibilities

A.9 Access Control A.9.4 System and Application

https://www.isms.online/iso-27001/annex-a-10-cryptography/

A.10 Cryptography A.10.1 Cryptographic Controls

https://www.isms.online/iso-27001/annex-a-12-operations-security/

A.12 Operations Security A.12.3 Information Backup

A.12 Operations Security A.12.4 Logging and Monitoring

A.12 Operations Security A.12.6 Technical Vulnerability Management

https://www.isms.online/iso-27001/annex-a-13-communications-security/

A.13 Communications Security A.13.1 Network Security Management

https://www.isms.online/iso-27001/annex-a-14-system-acquisition-development-and-maintenance/

A.14 System acquisition, dev & maintenance A.14.2 Security in Dev & Support

https://www.isms.online/iso-27001/annex-a-18-compliance/

A.18 Compliance A.18.1 Compliance with Legal and Regulatory Reqs

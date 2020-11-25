# sonar-cloudformation-plugin
Cloudformation template rules (cfn-nag (https://github.com/stelligent/cfn_nag)

[![license](https://img.shields.io/github/license/Hack23/sonar-cloudformation-plugin.svg)](https://github.com/Hack23/sonar-cloudformation-plugin/raw/master/LICENSE.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.hack23.sonar/sonar-cloudformation-plugin.svg)](http://mvnrepository.com/artifact/com.hack23.sonar/sonar-cloudformation-plugin)
[![Jenkins](https://img.shields.io/jenkins/s/https/www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master.svg)](https://www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master/)
[![Jenkins tests](https://img.shields.io/jenkins/t/https/www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master.svg)](https://www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master/lastCompletedBuild/testReport/)
[![Coverage](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=coverage)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Lines of Code](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=ncloc)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Technical Debt](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=sqale_index)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Quality Gate](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=alert_status)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Black Duck Security Risk](https://copilot.blackducksoftware.com/github/repos/Hack23/sonar-cloudformation-plugin/branches/master/badge-risk.svg)](https://copilot.blackducksoftware.com/github/repos/Hack23/sonar-cloudformation-plugin/branches/master)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin?ref=badge_shield)
[![SourceSpy Dashboard](https://sourcespy.com/shield.svg)](https://sourcespy.com/github/hack23sonarcloudformationplugin/)

# Runtime

[![JDK-8](https://img.shields.io/badge/jdk-8-green.svg)]
[![JDK-11 or higher](https://img.shields.io/badge/jdk-11-green.svg)]
[![JDK-12](https://img.shields.io/badge/jdk-12-orange.svg)]
[![JDK-13](https://img.shields.io/badge/jdk-13-orange.svg)]


# Demo links

Demo Sonarqube quality profile : https://www.hack23.com/sonar/profiles/show?language=yaml&name=Cloudformation+Rules

Demo Sonarqube quality rules : https://www.hack23.com/sonar/coding_rules?languages=yaml&repositories=cfn-yaml

Demo Sonarqube quality issues : https://www.hack23.com/sonar/project/issues?id=com.hack23.cia%3Acia-all&languages=yaml&resolutions=WONTFIX


# Support

Sonarqube 7.7+ and currently only supports cfn_nag reports (https://github.com/stelligent/cfn_nag)

Requires Yaml plugin and optional Json plugin if json is used for cloudformation templates.
Yaml plugin https://github.com/sbaudoin/sonar-yaml
Json plugin https://github.com/racodond/sonar-json-plugin


# Howto

Prepare cfn_nag reports running (cfn_nag --output-format=json src/main/config/template.yml > target/template.yml.nag)
and set the property sonar.cfn.nag.reportFiles=target/template.yml.nag (comma separated if multiple reports)

Or scan directories using cfn_nag_scan running (cfn_nag_scan  --input-path src/main/config/ -o json -> target/cfn-nag-scan.nagscan) and set the property sonar.cfn.nag.reportFiles=target/cfn-nag-scan.nagscan

# Properties supported

sonar.cfn.nag.reportFiles=target/template.yml.nag,target/cfn-nag-scan.nagscan

One or multiple .nag or .nagscan files, note for .nag files the filename should be template filename appended with .nag and for nag_scan any filename with .nagscan suffix.



# Group the cfn-nag rules to CWE

CWE-311 - Missing Encryption of Sensitive Data https://cwe.mitre.org/data/definitions/311

CWE-326 - Inadequate Encryption Strength https://cwe.mitre.org/data/definitions/326

CWE-732 - Incorrect Permission Assignment for Critical Resource https://cwe.mitre.org/data/definitions/732

CWE-257 - Storing Passwords in a Recoverable Format https://cwe.mitre.org/data/definitions/257

CWE-778 - Insufficient Logging https://cwe.mitre.org/data/definitions/778

CWE-272 - Least Privilege Violation https://cwe.mitre.org/data/definitions/272

CWE-286 - Incorrect User Management https://cwe.mitre.org/data/definitions/286

CWE-770 - Allocation of Resources Without Limits or Throttling https://cwe.mitre.org/data/definitions/770

No CWE associated

# Group the cfn-nag rules to NIST 800-53

800-53-AC-4 AC-4 INFORMATION FLOW ENFORCEMENT https://nvd.nist.gov/800-53/Rev4/control/AC-4

800-53-AC-6 AC-6 LEAST PRIVILEGE https://nvd.nist.gov/800-53/Rev4/control/AC-6

800-53-AU-12 AU-12 AUDIT GENERATION https://nvd.nist.gov/800-53/Rev4/control/AU-12

800-53-IA-5 IA-5 AUTHENTICATOR MANAGEMENT https://nvd.nist.gov/800-53/Rev4/control/IA-5

800-53-SC-8 SC-8 TRANSMISSION CONFIDENTIALITY AND INTEGRITY https://nvd.nist.gov/800-53/Rev4/control/SC-8

800-53-SC-12 SC-12 CRYPTOGRAPHIC KEY ESTABLISHMENT AND MANAGEMENT https://nvd.nist.gov/800-53/Rev4/control/SC-12

800-53-SC-13 SC-13 CRYPTOGRAPHIC PROTECTION https://nvd.nist.gov/800-53/Rev4/control/SC-13

800-53-CP-9 CP-9 INFORMATION SYSTEM BACKUP https://nvd.nist.gov/800-53/Rev4/control/CP-9


# Roadmap

Support more cloudformation checkers and add more rules for cfn_nag.

## License
[![FOSSA Details](https://app.fossa.io/api/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin?ref=badge_large)

pipeline {
   agent any
  
   tools { 
        maven 'Maven' 
        jdk 'Java11' 
    }
    
   stages {

	   stage('Build') {
	      steps {
	         sh "mvn clean install site"
	         sh "rm -rf target/site/jacoco-aggregate"
	         sh "rm -rf target/site/jacoco-ut"
	         sh "rm -rf target/site/jacoco-it"	         	         
	      }
	        post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    
                    jacoco( 
				      execPattern: 'target/jacoco.exec',
				      classPattern: 'target/classes',
				      sourcePattern: 'src/main/java',
				      exclusionPattern: 'src/test*'
				   )
                    
                }
            }
	   }
	   
	    stage('Record Coverage') {
            when { branch 'master' }
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                 }
                step([$class: 'MasterCoverageAction', scmVars: [GIT_URL: env.GIT_URL]])
            }
        }
        
        stage('PR Coverage to Github') {
            when { allOf {not { branch 'master' }; expression { return env.CHANGE_ID != null }} }
            steps {
                script {
                    currentBuild.result = 'SUCCESS'
                 }
                step([$class: 'CompareCoverageAction', publishResultAs: 'statusCheck', scmVars: [GIT_URL: env.GIT_URL]])
            }
       }

	   stage('Vulnerability Check') {
	   	  tools { 
    	    jdk 'Java8' 
	    	}
	   
	      steps {
	         sh "mvn org.owasp:dependency-check-maven:check -Dformat=ALL -DsuppressionFile=src/config/suppressions.xml"
	      }
	   }


	   stage('Dependency Update Check') {
	      steps {
	         sh "mvn org.codehaus.mojo:versions-maven-plugin:2.7:dependency-updates-report -DdependencyUpdatesReportFormats=html,xml"
	      }
	   }
       
	   
	   stage('Publish results to Sonarqube') {
	      steps {
	         sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar -Dsonar.host.url=http://192.168.1.15:9000/sonar/ -Dsonar.sources=. -Dsonar.dependencyCheck.reportPath=target/dependency-check-report.xml -Dsonar.dependencyCheck.htmlReportPath=target/dependency-check-report.html"
	      }
	   }
	  
	   stage('Quality Gate') {
	      steps {
	         sh "mvn com.hack23.maven:sonar-quality-gates-maven-plugin:1.0.0:inspect  -Dsonar.host.url=http://192.168.1.15:9000/sonar"
	      }
	   }
	   
	   
	   stage('Results') {
	      steps {
	          archive 'target/*.jar'
	      }
	   }
   }
}
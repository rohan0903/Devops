node {
stage('checkout')
git credentialsId: '939747'
url: 'https://github.com/rohan0903/Devops.git'
node {
   echo 'Hello World'
}
stage('build/compile')
script: 'mvn clean package'
stage('archive the artifacts')
archiveArtifacts 'target/*.jar'
stage('emailnotification')
emailext body: 'Hi Rohan', subject: 'message', to: 'mogadampally.rohan@capgemini.com'
}

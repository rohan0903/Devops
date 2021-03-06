repo:node {
stage('checkout')
git credentialsId: '8520f0a3-f733-41f6-993b-c3bf196cd7ba',url: 'https://github.com/rohan0903/Devops.git'
stage('build/compile')
sh label: '',script: 'mvn clean compile package'
stage('archive the artifacts')
archiveArtifacts 'target/*.jar'
//stage('nexus artifact uploader')
//nexusartifactUploader artifacts: [[artifactId: 'myproject-0.0.1-SNAPSHOT', classifier: '', file: '/var/lib/jenkins/workspace/Spring_boot_Pipeline/target/myproject-0.0.1-SNAPSHOT.jar', type: 'jar']], credentialsId: '4e4d43ee-7c26-459f-a5c2-b36a6896266e', groupId: 'com.cg', nexusUrl: '34.245.61.112:8000', nexusVersion: 'nexus3', protocol: 'http', repository: 'Springboot', version: '1.0'
stage('jfrog artifact uploader')
rtMaven.tool= 'Maven-3.6.3'
rtMaven.deployer releaseRepo: 'libs-release-local' , snapshotRepo: 'libs-snapshot-local' , server: server
rtMaven.resolver releaseRepo:'libs-release' , snapshotRepo: 'libs-snapshot' , server: server
rtMaven.deployer.artifactDeploymentPatterns.addExclude("pom.xml")
buildInfo = Artifactory.newBuildInfo()
buildInfo.retention maxBuilds: 20, maxDays: 7, deleteBuildArtifacts: true
buildInfo.env.capture = true
}  
    

    
     

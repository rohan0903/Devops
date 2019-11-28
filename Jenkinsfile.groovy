
pipeline {
    stages {


        stage('Git Checkout') {
            steps {
                git credentialsId: '939747'
                url: 'https://github.com/rohan0903/Devops.git'
            }
        

        stage('build/compile') {
            steps {
                sh "mvn clean package"
            }
        }

        stage('push to artifactory') {

            steps {

            script{
                
                fetch_tags()

               if (env.BRANCH_NAME == 'master') {
                    echo 'I only execute on the master branch'
                    configFileProvider([configFile(fileId: 'our_settings', variable: 'SETTINGS')]) {
                    sh "mvn -s $SETTINGS deploy -DskipTests -Dbuild.version=${gitTagLatest()}.${env.BUILD_NUMBER} -Dartifactory_url=${env.ARTIFACTORY_URL} -Dartifactory_name=${env.ARTIFACTORY_NAME}"
                    }

                    echo 'tagging build'
                    tag_build();

                    } else if (env.BRANCH_NAME == 'develop') {
                    echo 'I only execute on the develop branch'
                    configFileProvider([configFile(fileId: 'our_settings', variable: 'SETTINGS')]) {
                    sh "mvn -s $SETTINGS deploy -DskipTests -Dbuild.version=1.0.0 -Dartifactory_url=${env.ARTIFACTORY_URL} -Dartifactory_name=${env.ARTIFACTORY_NAME}"
                    }

                }
                else {
                    echo 'I execute elsewhere'
                }

            }

            }
        }



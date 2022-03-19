def status
def status_error = 'NoErrors'
def md5sum_error = 'NoErrors'
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:''' 
          sudo -S docker run --name mynginx -p 9889:80 -d -v $WORKSPACE/index/:/usr/share/nginx/html/ nginx < /tmp/pass
        ''')
      }
    }
    stage('Check Nginx Status') {
      steps {
        sh 'curl -s -o /dev/null -w "%{http_code}" localhost:9889 > status'
        script {
          status = sh (
            script: 'cat status',
            returnStdout: true
          ).trim()
          if("${status}"=="200") {
            println("Nginx is UP correctly!")
          } else {
            println("Nginx is UP incorrectly! Status = ${status}")
	    status_error = 'Error'
          }
        }
      }
	}
    stage('Check md5sum') {
      steps {
        script {
          def sum = sh (
            script: 'cat index/index.html | md5sum',
            returnStdout: true
          ).trim()
	  println("Index.html md5sum = ${sum}")
          def result = sh (
            script: 'curl localhost:9889 | md5sum',
            returnStdout: true
          ).trim()
          println("Index.html curl md5sum = ${result}")
	  if("${result}" == "${sum}") {
            println("Index.html is correct!")
          } else {
            println("Index.html is incorrect!")
	    md5sum_error = 'Error'
          }
        }
      }
    }
  }
}

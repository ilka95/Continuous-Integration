
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:''' 
          sudo -S docker run --name mynginx -p 9889:80 -d -v /$WORKSPACE/index/:/usr/share/nginx/html/ nginx < /tmp/pass
        ''')
      }
    }
  }
}

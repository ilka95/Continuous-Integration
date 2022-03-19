
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:'''
          sudo -S docker stop mynginx < /tmp/pass
          sudo -S docker rm mynginx < /tmp/pass    
          sudo -S docker run --name mynginx -p 9889:80 -d -v index/:/usr/share/nginx/html/ nginx < /tmp/pass
        ''')
      }
    }
  }
}

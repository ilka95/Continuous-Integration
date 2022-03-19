sudo -S docker run --name mynginx -p 9889:80 -d -v $WORKSPACE/index/:/usr/share/nginx/html/ nginx < /tmp/pass
sleep 5
set good='200'
set status=$(curl -s -o /dev/null -w "%{http_code}" localhost:9889)
if ["$status" == "$good"] 
then echo 'Nginx is UP correctly!'
exit 0
else echo 'Nginx is UP incorrectly! Status = $status'
exit 1
fi
set sum=$(cat index/index.html | md5sum)
set curlsum=$(curl localhost:9889 | md5sum)
if ["$result" == "$sum"]
then echo 'Index.html is correct!'
exit 0
else echo 'Index.html is incorrect!'
exit 1
fi
sudo -S docker stop mynginx < /tmp/pass
sudo -S docker rm mynginx < /tmp/pass

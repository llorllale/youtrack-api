FROM java:8-jre 

RUN mkdir -p /opt/youtrack/data /opt/youtrack/backup /opt/youtrack/bin

COPY youtrack/data /opt/youtrack/data
COPY youtrack/backup /opt/youtrack/backup

WORKDIR /opt/youtrack

RUN apt-get update && apt-get install -y supervisor && rm -rf /var/lib/apt/lists/*

RUN wget https://download.jetbrains.com/charisma/youtrack-${youtrack.version}.jar -O /opt/youtrack/bin/youtrack.jar

ADD supervisor/youtrack.conf /etc/supervisor/conf.d/youtrack.conf

EXPOSE 80/tcp 

CMD ["/usr/bin/supervisord","-n","-c","/etc/supervisor/supervisord.conf"]
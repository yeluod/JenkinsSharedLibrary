FROM nginx:1.22.1

LABEL developer.name='yeluo' \
      developer.email='yeluo0528@gmail.com' \
      version='1.0.0' \
      description='Node Project'

EXPOSE 80

COPY dist/ /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/conf.d/default.conf

ENV TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
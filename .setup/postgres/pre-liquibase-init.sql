-- note this file is also used for the local developer setup /docker/docker-compose file
REVOKE ALL ON schema public FROM public;

DROP USER IF EXISTS exam_app_user;
CREATE user exam_app_user WITH ENCRYPTED PASSWORD 'password';

CREATE SCHEMA IF NOT EXISTS exam;
ALTER SCHEMA exam OWNER TO exam_app_user;

commit;

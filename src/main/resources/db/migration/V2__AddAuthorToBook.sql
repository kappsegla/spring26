ALTER table book
    ADD column author varchar(255);

UPDATE book
SET author='Unknown'
WHERE author IS NULL;

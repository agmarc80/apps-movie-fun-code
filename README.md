# Movie Fun!

Smoke Tests require server running on port 8080 by default.

## Build WAR ignoring Smoke Tests

```
$ mvn clean package -DskipTests -Dmaven.test.skip=true
```

## Run Smoke Tests against specific URL

```
$ MOVIE_FUN_URL=http://moviefun.example.com mvn test


Host github.com-ram
        HostName github.com
        User git
        IdentityFile ~/.ssh/ram_id_rsa

Host github.com-deepak
        HostName github.com
        User git
        IdentityFile ~/.ssh/id_rsa_deepak

```

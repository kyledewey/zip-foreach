# Zip-Foreach #

Runs a command for each file in a zipfile.

For ease of use, it's best to first make a jar file, like so:

```console
sbt
> compile
> assembly
```

This will create the file `target/scala-*/zip_collate-assembly-1.0.jar`.
From there, you can run it as follows:

```console
java -jar target/scala-*/zip_collate-assembly-1.0.jar zipfile.zip my command
```

...where `zipfile.zip` is a zipfile containing files, and `my command` is the command `my` with the parameter `command`.
At runtime, Zip-Foreach will individually write each file from the zipfile to its own file, and execute `my command FILE`, where `FILE` is the file that it created.
Note that it is the responsibility of the command to delete the file.

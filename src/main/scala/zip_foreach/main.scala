package zip_foreach

object Main {
  def usage() {
    println("Takes the following parameters:")
    println("-Name of the zipfile")
    println("-Parameters for the command.  Implicit last parameter is file with content.")
  }

  def main(args: Array[String]) {
    if (args.length < 2) {
      usage()
    } else {
      val reader = new ZipfileReader(args(0))
      val maker = new ProcessMaker(args.toSeq.drop(1))
      reader.foreach(input => {
        input.writeFile()
        maker.runProcessWithFilename(input.filename)
      })
    }
  } // main
} // Main


package zip_foreach

class ProcessMaker(args: Seq[String]) {
  val reversedArgs: List[String] = args.reverse.toList

  def runProcessWithFilename(filename: String) {
    import scala.sys.process.Process
    Process((filename :: reversedArgs).reverse.toSeq).!
  }
} // ProcessMaker

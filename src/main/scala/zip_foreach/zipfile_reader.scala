package zip_foreach

case class ZipfileReadEntry(filename: String, contents: Seq[String]) {
  def writeFile() {
    import java.io.{File, PrintWriter}
    val writer = new PrintWriter(new File(filename))
    contents.foreach(writer.println)
    writer.close()
  }
} // ZipfileReadEntry

class ZipfileReader(zipfileName: String) extends Iterator[ZipfileReadEntry] {
  import java.io.{BufferedInputStream,
		  FileInputStream,
		  BufferedReader,
		  InputStreamReader}
  import java.util.zip.ZipInputStream

  // ---BEGIN CONSTRUCTOR---
  private val input =
    new ZipInputStream(
      new BufferedInputStream(
	new FileInputStream(zipfileName)))
  private var nextItem = nextEntry()
  // ---END CONSTRUCTOR---

  // not thread safe
  private def nextEntry(): Option[ZipfileReadEntry] = {
    val zipEntry = input.getNextEntry()
    if (zipEntry eq null) {
      input.close()
      return None
    }

    val reader =
      new BufferedReader(
	new InputStreamReader(input))

    @scala.annotation.tailrec
    def loop(accum: List[String]): Seq[String] = {
      reader.readLine match {
	case null => {
	  accum.reverse.toSeq
	}
	case s => {
	  loop(s :: accum)
	}
      }
    }

    Some(ZipfileReadEntry(zipEntry.getName, loop(List())))
  }

  def hasNext(): Boolean = nextItem.isDefined

  def next(): ZipfileReadEntry = {
    assert(hasNext())
    val retval = nextItem.get
    nextItem = nextEntry()
    retval
  }
}

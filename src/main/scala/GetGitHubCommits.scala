import java.text.SimpleDateFormat
import java.util.TimeZone

import scala.xml.XML

object GetGitHubCommits {
  def init(userName: String, repoName: String, branchName:String = "master") = {
    val url = s"https://github.com/${userName}/${repoName}/commits/${branchName}.atom"
    url
  }

  def getXML(url:String) = {
    val xmlContents = XML.load(url)
    xmlContents
  }

  def getXMLParser(xml:scala.xml.Elem) = {
    val entry = xml \ "entry"
    entry
  }

  def changeUTCtoDate(date:String, timezone: String = "Asia/Seoul") = {
    val UTC = TimeZone.getTimeZone("UTC")
    val zone = TimeZone.getTimeZone(timezone)
    val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    simpleDateFormat.setTimeZone(UTC)
    val s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val newDate = simpleDateFormat.parse(date)
    s.setTimeZone(zone)
    s.format(newDate)
  }

  def get(userName: String, repoName: String, branchName:String = "master") = {
    val url = init(userName, repoName, branchName)
    val xml = getXML(url)
    val entry = getXMLParser(xml)
    val authors = entry.flatMap(child => (child \\ "author" \ "name").map(_.text))
    val titles = entry.flatMap(child => (child \\ "title").map(_.text))
    val dates = entry.flatMap(child => (child \\ "updated").map(a => changeUTCtoDate(a.text)))
    val info = authors zip titles zip dates
    val infoFlatten = info map { case (((i,c),s)) => (i,c,s)}
    infoFlatten
  }
}

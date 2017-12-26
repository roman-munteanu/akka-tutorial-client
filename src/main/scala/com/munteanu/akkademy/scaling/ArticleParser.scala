package com.munteanu.akkademy.scaling

import de.l3s.boilerpipe.extractors.ArticleExtractor

object ArticleParser {
//  def apply(html: String): String = ArticleExtractor.INSTANCE.getText(html)
  def apply(html: String): String = html.replaceAll("\\<.*?\\>", "")
}

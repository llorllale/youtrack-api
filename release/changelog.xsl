<?xml version="1.0"?>
<!--

    Copyright 2017 George Aristy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="text"/>
  <xsl:variable name="newLine"><xsl:text>&#xa;</xsl:text></xsl:variable>
  <xsl:template match="log">## CHANGELOG
    <xsl:for-each select="commits/commit">
      <xsl:value-of select="$newLine"/>* <xsl:value-of select="message/short"/>
      <xsl:value-of select="$newLine"/>
      <xsl:text>  </xsl:text><xsl:value-of select="id"/> (by <xsl:value-of select="author/name"/>)</xsl:for-each>
  </xsl:template>
</xsl:stylesheet>

<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output version="1.0" indent="yes" encoding="UTF-8" method="html"/>
	<xsl:template match="/">
		<html>
			<body>
				<h2>XSLT 转换</h2>
				<table border="1">
					<xsl:for-each select="document/keys">
						<tr>
							<xsl:for-each select="key">
								<xsl:choose>
									<xsl:when test="k3/@is">
										<td>
											<xsl:value-of select="k1"/>&#215;<xsl:value-of select="k2"/>=<span style="color:red ">
												<xsl:value-of select="k3"/>
											</span>
										</td>	
									</xsl:when>
									
									
									<xsl:otherwise>
										<td>
											<xsl:value-of select="k1"/>&#215;<xsl:value-of select="k2"/>=<xsl:value-of select="k3"/>
										</td>	
									</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
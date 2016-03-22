<dl>
	<dt>
		<h3><a href="<#if a.onlyUrl>content/redirect.do<#else>content.html</#if>?id=${a.id}" target="_blank">${a.title}</a><span>${a.publishDate?date}</span></h3>
	</dt>
	<dd>
		<a href="<#if a.onlyUrl>content/redirect.do<#else>content.html</#if>?id=${a.id}" target="_blank">
<#if a.cover?has_content>
			<img src="<@_thumb path=a.cover width=144 height=192/>" alt="${a.title}"/>
</#if>
<#if a.hasImages>
		<@_contentFilesList contentId=a.id count=2>
			<#list page.list as i>
				<img src="<@_thumb path=a.cover width=144 height=192/>" alt="${a.title}"/>
			</#list>
		</@_contentFilesList>
</#if>
		</a>
		<p class="clearfix-before"><@t.cut a.description!'' 100 '...'/><a href="<#if a.onlyUrl>content/redirect.do<#else>content.html</#if>?id=${a.id}" target="_blank"> 详细 &gt;&gt;</a></p>
	</dd>
</dl>
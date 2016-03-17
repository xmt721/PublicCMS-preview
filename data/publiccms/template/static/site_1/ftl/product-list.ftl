<div class="col-md-3">
	<a href="<#if a.onlyUrl><@_cmsPath/>?id=${a.id}<#else>${a.url!}</#if>">
<#if a.cover?has_content>
		<img src="<@_thumb path=a.cover width=400 height=300>" alt="${a.title}"/>
</#if>
		<h3>${a.title}</h3>
		<p><@t.cut a.description!'' 100 '...'/></p>
	</a>
</div>
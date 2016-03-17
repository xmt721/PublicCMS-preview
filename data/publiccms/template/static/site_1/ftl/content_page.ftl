<#if page??>
	<div class="page">
		<#if !page.firstPage>
			<a href="${getPage(url,page.prePage)}">上一页</a>
		<#else>
			<span>上一页</span>
		</#if>
		<#list page.list as a><a href="${getPage(url,a?counter)}"<#if url?counter=page.pageIndex> class="selected"</#if>>${a?counter}</a></#list>
		<#if !page.lastPage>
			<a href="${getPage(url,page.nextPage)}">下一页</a>
		<#else>
			<span>下一页</span>
		</#if>
	</div>
</#if>
<header>
	<div class="container search">
		<form action="${site.dynamicPath}search.html">
			<input name="word" type="text" placeholder="这里输入关键词"/>
			<input type="submit" value="搜索"/>
		</form>
	</div>
	<div class="container">
		<div class="logo">
			<a href="${site.dynamicPath}"><img src="${site.resourcePath!}image/logo.png"/></a>
			<a href="http://shang.qq.com/wpa/qunwpa?idkey=8a633f84fb2475068182d3c447319977faca6a14dc3acf8017a160d65962a175"><img src="http://pub.idqqimg.com/wpa/images/group.png" alt="Public CMS" title="Public CMS"></a>
		</div>
		<nav>
			<ul>
				<li><a href="${site.dynamicPath}">首页</a></li>
<@_categoryList>
	<#list page.list as a>
				<li>
					<a href="${site.dynamicPath}category.html?id=${a.id}">${a.name}</a>
		<#if a.childIds?has_content>
					<ul>
			<@_categoryList parentId=a.id>
				<#list page.list as a>
						<li><a href="${site.dynamicPath}list.html?id=${a.id}">${a.name}</a></li>
				</#list>
			</@_categoryList>
					</ul>
		</#if>
				</li>
	</#list>
</@_categoryList>
			</ul>
		</nav>
	</div>
</header>
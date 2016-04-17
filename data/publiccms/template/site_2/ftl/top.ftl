		<div class="right texright clearfix">
<#if user??>
			<a href="/user.html">${user.nickName?html}</a><a href="logout.do">退出</a>
<#else>
			<a href="/login.html">登录</a>
			<a href="/register.html">注册</a>
</#if>
		</div>
		<nav>
			<a href="/" class="logo">HOME</a>
		</nav>
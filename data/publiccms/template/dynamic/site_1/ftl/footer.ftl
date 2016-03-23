<footer>
	<div class="container">
		<@_pageDataList path="/include/index.html/business.html">
			<@include path=path/><#-- 页尾导航 -->
		</@_pageDataList>
		<@_pageDataList path="/include/index.html/support.html">
			<@include path=path/><#-- 技术支持 -->
		</@_pageDataList>
	</div>
	<div class="container">
		<div class="copyright">&copy;${.now?string('yyyy')} PublicCMS.com All Rights Reserved. 京ICP备15009690号</div>
	</div>
</footer>
<@_metadata path='/index.html' type='static'>${object.extendData.code}</@_metadata>
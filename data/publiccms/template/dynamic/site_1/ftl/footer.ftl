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
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?bd81f02e5329554415de9ee15f916a98";
  var s = document.getElementsByTagName("script")[0];
  s.parentNode.insertBefore(hm, s);
})();
</script>
<!DOCTYPE html>
<html lang="en">
<#include "/include/include.ftl">
<title>${fileName!}</title>
<body>
<iframe src="${ctx}/previewFile?filePath=${RequestParameters["filePath"]!}" width="100%" height="100%"></iframe>
</body>
</html>

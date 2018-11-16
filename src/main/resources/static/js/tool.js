/**
 *	js工具类
 */
function page(n,s,a){
    if(n) $("#pageNo").val(n);
    if(s) $("#pageSize").val(s);
    $("#searchForm").submit();
    return false;
}
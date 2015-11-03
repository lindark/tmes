$.extend($.validity.messages, {
    require:"#[field]为必填项。",
    match:"#[field]格式错误。",
    integer:"#[field]必须是整数。",
    date:"#[field]应为日期格式（yyyy-MM-dd）。",
    email:"#[field]格式错误。",
    url:"#[field]URL格式错误。",
    number:"#[field]必须为数字。",
    phone:"#[field]格式错误。",
    lessThan:"#[field]必须小于#[max]。",
    lessThanOrEqualTo:"#[field]必须小于等于#[max]。",
    greaterThan:"#[field]必须大于 #[min].",
    greaterThanOrEqualTo:"#[field]必须大于等于#[min]。",
    range:"#[field]必须在#[min]和#[max]之间。",
    tooLong:"#[field]不能超过 #[max] 个字符。",
    tooShort:"#[field]不能少于#[min] 个字符。",
    equal:"值不相等。",
    distinct:"值重复。",
    float_1:"#[field]只能为1位小数。",
    float_2:"#[field]只能为2位小数。",
    float_3:"#[field]只能为3位小数。",
    float_4:"#[field]只能为4位小数。",
    float_5:"#[field]只能为5位小数。"
});

$.extend($.validity.patterns, {
    date: /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/,
    number: /^[0-9]*$/,
    float_1: /^[0-9]+(.[0-9]{0,1})?$/,
    float_2: /^[0-9]+(.[0-9]{0,2})?$/,
    float_3: /^[0-9]+(.[0-9]{0,3})?$/,
    float_4: /^[0-9]+(.[0-9]{0,4})?$/,
    float_5: /^[0-9]+(.[0-9]{0,5})?$/,
    phone: /^[0-9+][0-9][0-9-]{3,29}[0-9]$/,
    email: /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/
});

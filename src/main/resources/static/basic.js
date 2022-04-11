let targetId;

$(document).ready(function () {
    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();

    showProduct();
})


function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//////////////////////////////////////////////////////////////////////////////////////////
///// 여기 아래에서부터 코드를 작성합니다. ////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////


function execSearch() {
    /**
     * 검색어 input id: query
     * 검색결과 목록: #search-result-box
     * 검색결과 HTML 만드는 함수: addHTML
     */
    $("#search-result-box").empty();
    // 1. 검색창의 입력값을 가져온다.
    let query = $('#query').val();

    // 2. 검색창 입력값을 검사하고, 입력하지 않았을 경우 focus.
    if (query == '') {
        alert('검색어를 입력해주세요');
        $('#query').focus();
        return;
    }
    // 3. GET /api/search?query=${query} 요청
    $.ajax({
        type: 'GET',
        url: `/api/search?query=${query}`,
        success: function (response) {
            $('#search-result-box').empty();
            // 4. for 문마다 itemDto를 꺼내서 HTML 만들고 검색결과 목록에 붙이기!
            for (let i = 0; i < response.length; i++) {
                let itemDto = response[i];
                let tempHtml = addHTML(itemDto);
                $('#search-result-box').append(tempHtml);
            }
        }
    })

}

function addHTML(itemDto) {
    /**
     * class="search-itemDto" 인 녀석에서
     * image, title, lprice, addProduct 활용하기
     * 참고) onclick='addProduct(${JSON.stringify(itemDto)})'
     *
     * json 형태를 그대로 넣을수 없어서 씀
     *
     */
    return `<div class="search-itemDto">
        <div class="search-itemDto-left">
            <img src="${itemDto.image}" alt="">
        </div>
        <div class="search-itemDto-center">
            <div>${itemDto.title}</div>
            <div class="price">
                ${numberWithCommas(itemDto.lprice)}
                <span class="unit">원</span>
            </div>
        </div>
        <div class="search-itemDto-right">
            <img src="images/icon-save.png" alt="" onclick='addProduct(${JSON.stringify(itemDto)})'>
            
        </div>
    </div>`
}
function addProduct(itemDto) { //문자열화 되어져 넘어온 데이터가 다시 json 형태로 바뀐다
    /**
     * modal 뜨게 하는 법: $('#container').addClass('active');
     * data를 ajax로 전달할 때는 두 가지가 매우 중요
     * 1. contentType: "application/json",
     * 2. data: JSON.stringify(itemDto),
     */
    // 1. POST /api/products 에 관심 상품 생성 요청
    $.ajax({
        type:"POST",
        url:"/api/products",
        data:JSON.stringify(itemDto),
        contentType:"application/json",
        /**
         * ajax로 서버에 데이터를 보낼때, header 중 content type이 존재하는데, 디폴트값으로 application/x-www-form-urlencoded; charset=UTF-8 지정된다.
         * json 형태의 데이터를 주고 싶을때 지정해줘야한다.
         *
         * request 안에 포함된 json 형태의 데이터를 받았을 때, 이것을 보통 VO(혹은 DTO)에 다시 담아 사용하는데,,
         * .ajax는 데이터를 문자열화 해주지 않기 때문에 보낼 데이터를 JSON.stringify()로 감싸주어야 한다.
         * 그렇지 않을 시,,* json데이터의 "key":"value" 형태의 패턴을 인식하지 못한다.
         *
         * contentType: 보내는 데이터의 타입입         **/
        success:function(response){
            $('#container').addClass('active');
            targetId = response.id;
        }
    })
    // 2. 응답 함수에서 modal을 뜨게 하고, targetId 를 reponse.id 로 설정 (숙제로 myprice 설정하기 위함)
}

function showProduct() {
    /**
     * 관심상품 목록: #product-container
     * 검색결과 목록: #search-result-box
     * 관심상품 HTML 만드는 함수: addProductItem
     */
    // 1. GET /api/products 요청
    $.ajax({
        type:"GET",
        url:"/api/products",
        success: function(response){
            $("#product-container").empty();
            $("#search-result-box").empty();

            for (let i=0;i<response.length; i++){
                let product = response[i]
                let tempHtml = addProductItem(product);
                $("#product-container").append(tempHtml);
            }
        }
    })
    // 2. 관심상품 목록, 검색결과 목록 비우기
    // 3. for 문마다 관심 상품 HTML 만들어서 관심상품 목록에 붙이기!
}

function addProductItem(product) {
    // link, image, title, lprice, myprice 변수 활용하기
    return `<div class="product-card" onclick="window.location.href='${product.link}'">
            <div class="card-header">
                <img src="${product.image}"
                     alt="">
            </div>
            <div class="card-body">
                <div class="title">
                    ${product.title}
                </div>
                <div class="lprice">
                    <span>${numberWithCommas(product.lprice)}</span>원
                </div>
                <div class="isgood ${product.lprice <= product.myprice ? '' : 'none'}">
                    최저가
                </div>
            </div>
        </div>`;
}

function setMyprice() {
    /**
     * 숙제! myprice 값 설정하기.
     * 1. id가 myprice 인 input 태그에서 값을 가져온다.
     * 2. 만약 값을 입력하지 않았으면 alert를 띄우고 중단한다.
     * 3. PUT /api/product/${targetId} 에 data를 전달한다.
     *    주의) contentType: "application/json",
     *         data: JSON.stringify({myprice: myprice}),
     *         빠뜨리지 말 것!
     * 4. 모달을 종료한다. $('#container').removeClass('active');
     * 5, 성공적으로 등록되었음을 알리는 alert를 띄운다.
     * 6. 창을 새로고침한다. window.location.reload();
     */
    let myprice = $("#myprice").val();
    if (myprice == ""){
        alert("값을 입력해 주세요.");
        return;
    }
    $.ajax({
        type:"PUT",
        url:`/api/products/${targetId}`,
        data: JSON.stringify({'myprice':myprice}),
        contentType: "application/json",//문자열로 받아들이지말고 json으로 써라
        success: function(response){
            $('#container').removeClass('active');
            alert("관심 가격이 설정되었습니다");
            window.location.reload();

        }


    })
}
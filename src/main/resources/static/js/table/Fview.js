

$(function () {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    // 메뉴 열기
    $(".table-menu").click(function(event){
        var modal = $("#table-view-M");
        var iconPosition = $(this).offset();
        var iconHeight = $(this).outerHeight();

        // 모달의 위치를 클릭한 아이콘의 바로 아래로 설정
        modal.css({
            top: iconPosition.top + iconHeight + "px",
            left: (iconPosition.left)-110 + "px"
        });

        modal.css("display", "block");
    });
    // 메뉴 닫기 (x버튼)
    $(".close").click(function(){
        $("#table-view-M").css("display", "none");
    });
    // 메뉴 닫기 (모달 밖 클릭 시)
    $(document).mouseup(function(event){
        var modal = $("#table-view-M");
        if (!modal.is(event.target) && modal.has(event.target).length === 0) {
            modal.css("display", "none");
        }
    });

    // 댓글 기능
    $("#comment table").hide(); 					// 1
    let page = 1;
    const count = parseInt($("#count-comment").text());
    console.log(count);

    if (count != 0) { 								// 댓글 갯수가 0이 아니면
        getList(1); 						    // 첫 페이지의 댓글을 구해온다. (한 페이지에 3개씩 가져온다.)
    } else { 										// 댓글이 없는 경우
        $("#message").text("등록된 댓글이 없습니다.").css('text-align', 'center')
    }

    // 댓글 등록
    $("#write").click(function() {
        const comm = $('.write-content').val().trim();
        console.log(comm);

        if(!comm) {
            alert('내용을 입력하세요');
            return false;
        }
        enter = false;
        if(confirm('댓글을 등록하시겠어요?')) {
            url = "../comment/add";
            data = {
                "comm_content" : comm,
                "comm_id" : $("#loginid").val(),
                "comm_name" : $("#loginname").val(),
                "comm_profile" : $("#loginprofile").val(),
                "board_num" : $("#board_num").val()
            };
            enter = true;
        }
        console.log(header, token)
        if(enter) {
            $.ajax({
                type : "post",
                url : url,
                data : data,
                beforeSend : function(xhr)
                {   //데이터를 전송하기 전에 헤더에 csrf값을 설정합니다.
                    xhr.setRequestHeader(header, token);
                },
                success : function(result) {
                    if(result == 1) {
                        // page=1
                        getList(page);
                    }
                }
            })
        }

    });

    function getList(currentPage) {
        $.ajax({
            type	 : "post",
            url 	 : "../comment/list",
            data	 : {
                "board_num" : $("#board_num").val(),
                "page" 		: currentPage
            },
            dataType : "json",
            beforeSend : function(xhr)
            {   //데이터를 전송하기 전에 헤더에 csrf값을 설정합니다.
                xhr.setRequestHeader(header, token);
            },
            success	 : function(rdata) {
                console.log("보여줄 댓글 = " + rdata.list.length)


                // 댓글 갯수 다시 표시
                $("#count-comment").text(rdata.listcount);
                if(rdata.listcount > 0) {
                    $("#comment-box").show(); 	// 문서가 로딩될 때 hide()했던 부분을 보이게 한다. (1)
                    $("#comment-box").empty();
                    $(rdata.list).each(function() {
                        let output = '';
                        let img = '';
                        console.log("로그인한 아이디 = " + $("#loginid").val() + "/ 댓글의 아이디 = " + this.comm_id)
                        /*if($("#loginid").text() == this.comm_id) {
                            img = "<img src='../image/pencil.png' width='15px' class='update'>"
                                + "<img src='../image/trash.png' width='15px' class='remove'>"
                                + "<input type='hidden' value='" + this.comm_num + "' >";
                        }*/
                        output += "<div class=\"comment-box\" style=\"margin-bottom:20px;\">\n" +
                        "               <input type='hidden' value='" + this.comm_num + "' >" +
                        "               <div style=\"height: 25px;\">\n" +
                                            // 프사
                        "                   <img src=\"../image/" + this.comm_profile + "\" alt=\"../image/default.png\" style=\"width:30px !important; float: left; margin: 2px 15px 0px 0px;\">\n"

                        if($("#loginid").val() == this.comm_id) {
                            output += "     <i class=\"mdi mdi-dots-vertical\" style=\"float: right; font-size: 25px; margin-top:3px;\"></i>\n"
                        } else {
                            getUserInfo(function(userInfo) {
                                console.log(userInfo)
                                if (userInfo.authorities.includes('ROLE_TEAM') && userInfo.department === '관리부') {
                                    output += "<i class=\"mdi mdi-dots-vertical\" style=\"float: right; font-size: 25px; margin-top:3px;\"></i>\n";
                                }
                            }.bind(this));
                        }

                        output += "         <div style=\"height:40px; padding-top:0px;\">\n" +
                                                // 댓글 작성자
                        "                       <span class=\"card-title\" style=\"font-width: bold; font-size:13px\">\n" +
                                                    this.comm_name +
                        "                       </span>\n" +
                        "                       <p class=\"card-description\" style=\"font-size:10px\">\n" +
                        "                           부서명 <code style=\"font-size:10px\"> 직급 </code> <code style=\"font-size:10px; color:dimgray\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + this.comm_regdate + "</code>\n" +
                        "                       </p>\n" +
                        "                   </div>\n" +
                        "               </div>\n" +
                        "               <div class=\"comm-contentbox\" style=\"margin-left:45px; margin-top:8px; font-size:18px\">\n" +
                        "               </div>\n" +
                        "               <div class=\"iconbox\" style=\"display: flex; align-items: center; height: 20px;\">\n" +
                        "                   <i class=\"mdi mdi-tooltip-outline\" style=\"font-size: 15px; margin-top:20px; margin-left:45px;\"></i>\n" +
                        "                   <span class=\"card-description\" style=\"font-size:13px !important; margin:19px 20px 0px 5px\">0</span>\n" +
                        "               </div><br>\n" +
                        "           </div>\n"

                        $("#comment-box").append(output);

                        // append한 마지막 tr의 2번째 자식 td를 찾아 text()메서드로 content를 넣는다.
                        $(".comment-box:last").find(".comm-contentbox").html("<hr>" + this.comm_content + "<br>"); // 3

                    }) // each end

                    if(rdata.listcount > rdata.list.length) {	// 전체 댓글 갯수 > 현재까지 보여준 댓글 갯수
                        $("#message").text("더보기").css('text-align', 'center').hover(
                            function() {
                                $(this).css('cursor', 'pointer');
                            },
                            function() {
                                $(this).css('cursor', 'default'); // 마우스가 떠났을 때 기본 커서로 변경
                            });
                    } else {
                        $("#message").text("");
                    }
                } else {
                    $("#message").text("등록된 댓글이 없습니다.")
                    $("#comment tbody").empty();
                    $("#comment table").hide();					// 1
                }
            }
        })
    }
    // 더보기를 누르면 댓글page 내용이 추가됨.
    $("#message").click(function() {
        getList(++page);
    })


});

// 삭제 확인
function confirmSubmit(event) {
    // 확인 메시지를 표시
    var result = confirm("정말 삭제하시겠어요?");

    // "취소"를 선택한 경우 폼 제출을 중지
    if (!result) {
        event.preventDefault();
    }
}

// 권한 확인 메서드
function getUserInfo(callback) {
    $.ajax({
        type: "GET",
        url: "/scan/auth",
        dataType: "json",
        success: function(data) {
            callback(data);
        },
        error: function(error) {
            console.error("삭제 권한 없음", error);
        }
    });
}
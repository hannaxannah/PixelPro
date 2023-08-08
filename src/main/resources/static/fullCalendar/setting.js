//카카오 지도 api 호출
  window.onload = function(){
      document.getElementById("address_kakao1").addEventListener("click", function(){ //위치칸을 클릭하면
          //카카오 지도 호출
          new daum.Postcode({
              oncomplete: function(data) { //선택시 입력값 세팅
                  document.getElementById("address_kakao1").value = data.address; // 주소 넣기
              }
          }).open();
      });
      document.getElementById("editAddress_kakao1").addEventListener("click", function(){ //위치칸을 클릭하면
          //카카오 지도 호출
          new daum.Postcode({
              oncomplete: function(data) { //선택시 입력값 세팅
                  document.getElementById("editAddress_kakao1").value = data.address; // 주소 넣기
              }
          }).open();
      });
  }

var newEvent;
var editEvent;
// 캘린더 초기설정
$(document).ready(function() {

    var loadInitialCalendar = $.ajax({ //일정 불러오기
          url: "/calendar/event",
          method: "GET",
          dataType: "json"
        });

      loadInitialCalendar.done(function(data){ // loadInitialCalendar 로딩이 완료 된다면
      var hcalendar = $('#HomeCalendar').fullCalendar({
            themeSystem: 'bootstrap4',
             header: { //캘린더 헤더
               left: '', //왼쪽
              center: '', //중간
              right: '' //오른쪽
           },
            height : 200,
            initialDate: new Date(), //오늘 날짜 기준으로 초기설정
            defaultView: 'listDay', //오늘 일정
            selectable: false,
            editable: false,
            events: data
         });
      var calendar = $('#MainCalendar').fullCalendar({

        eventRender: function(event, element, view) { //일정만들기
         var startTimeEventInfo = moment(event.start).format('HH:mm'); //시작시간 포맷
         var endTimeEventInfo = moment(event.end).format('HH:mm'); //종료시간 포맷
         var startDateEventInfo = moment(event.start).format('YYYY/MM/DD'); //시작시간 포맷
         var endDateEventInfo = moment(event.end).format('YYYY/MM/DD'); //종료시간 포맷 자꾸 끝나는일이 +1되서 나와서 subtract해서 -1일
         var displayEventDate; //보여지는 시간
         var locationInfo; //위치정보
         var userInfo; //유저정보 0은 개발팀

         if(event.location == null){
            locationInfo = "없음";
         }else{
            locationInfo = event.location;
         }

         if(event.username == "0"){
            userInfo = "관리자";
         }else{
            userInfo = event.username;
         }
         if(event.allDay == false){ //프로젝트 체크가 안되있을때
           displayEventDate = startDateEventInfo+" "+startTimeEventInfo + " - " + endDateEventInfo+" "+endTimeEventInfo; //시작시간-끝나는시간
         }else{
           displayEventDate = startDateEventInfo; //시작날짜만
         }

          element.popover({ //마우스 hover할때 나오는 팝업
            title:    '<div class="popoverTitleCalendar">'+ event.title +'</div>',
            content:  '<div class="popoverInfoCalendar">' +
                      '<p><strong>카테고리 :</strong> ' + event.calendar + '</p>' +
                      '<p><strong>부서 :</strong> ' + event.type + '</p>' +
                      '<p><strong>작성자 :</strong> ' + userInfo + '</p>' +
                      '<p><strong>위치 :</strong> '+locationInfo + '</p>' +
                      '<p><strong>기간:</strong> ' + displayEventDate + '</p>' +
                      '<div class="popoverDescCalendar"><strong>내용 :</strong> '+ event.description +'</div>' +
                      '</div>',
            delay: {
               show: "800",
               hide: "50"
            },
            trigger: 'hover',
            placement: 'top',
            html: true,
            container: 'body'
          });

           var show_username, show_type = true, show_calendar = true;

           var username = $('input:checkbox.filter:checked').map(function() {
               return $(this).val();
           }).get();
           var types = $('#type_filter').val(); //필터 type
           var calendars = $('#calendar_filter').val(); //필터 calendars

           show_username = username.indexOf(event.username) >= 0; //이름찾기

           if (types && types.length > 0) { //타입이 하나라도 있다면
               if (types[0] == "all") { //디폴트 0은 전부표시
                   show_type = true;
               } else { //고른 type에 따라 표시
                   show_type = types.indexOf(event.type) >= 0;
               }
           }

           if (calendars && calendars.length > 0) { //calendars 체크한게 있다면
               if (calendars[0] == "all") { //아무것도 필터 안하면
                   show_calendar = true; //전부표시
               } else { //고른 calendars중에 고른게 있다면 그거 표시
                   show_calendar = calendars.indexOf(event.calendar) >= 0;
               }
           }
           return show_username && show_type && show_calendar;

             },
            header: { //캘린더 헤더
                  left: ' prevYear, nextYear, today', //왼쪽
                 center: 'prev, title, next', //중간
                 right: 'month,agendaWeek,agendaDay,listWeek' //오른쪽
              },
              views: {
                   month: {
                     columnFormat:'dddd'
                   },
                   agendaWeek:{
                     columnFormat:'ddd D/M',
                     eventLimit: false
                   },
                   agendaDay:{
                     columnFormat:'dddd',
                     eventLimit: false
                   },
                   listWeek:{
                     columnFormat:''
                   }
               },
              eventAfterAllRender: function(view) { //캘린더 랜더링 완료후에 실행되는 명령어
                  if(view.name == "month"){
                     $(".fc-content").css('height','auto');
                     $(".fc-content").css('padding','8px 20px 8px 22px');
                   }
              },
              eventDragStart: function(event, jsEvent, ui, view) { //이벤트 옮길때 실행
                   var draggedEventIsAllDay;
                   draggedEventIsAllDay = event.allDay;
              },
              eventDrop: function(event, delta, revertFunc, jsEvent, ui, view) {//이벤트 놓을떄 실행
                   $('.popover.fade.top').remove();
              },
              select: function(startDate, endDate, jsEvent, view) { //날짜 클릭시 실행
                var today = moment();
                var startDate;
                var endDate;

                if(view.name == "month"){ //보기상태가 '월'일때
                   startDate.set({ hours: today.hours(), minute: today.minutes() }); //시 분은 현재시간으로
                   startDate = moment(startDate).format('ddd DD MMM YYYY HH:mm');
                   endDate = moment(endDate).subtract('days', 1);
                   endDate.set({ hours: today.hours() + 1, minute: today.minutes() });//끝은 1시간추가
                   endDate = moment(endDate).add(1,"d").format('ddd DD MMM YYYY HH:mm');
                }else{
                   startDate = moment(startDate).format('ddd DD MMM YYYY HH:mm');
                   endDate = moment(endDate).format('ddd DD MMM YYYY HH:mm');
                }

                var $contextMenu = $("#contextMenu"); //날짜누르면 나오는창

                var HTMLContent = '<ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu" style="display:block;position:static;margin-bottom:5px;">' +
                                     '<li onclick=\'newEvent("'+startDate+'","'+endDate+'") \'> <a tabindex="-1" href="#">일정 추가</a></li>' +
                                     '<li class="divider"></li>' +
                                     '<li><a tabindex="-1" href="#">닫기</a></li>' +
                                   '</ul>';
                 $(".fc-body").unbind('click');
                 $(".fc-body").on('click', 'td', function (e) {

                     document.getElementById('contextMenu').innerHTML = (HTMLContent);

                     $contextMenu.addClass("contextOpened");
                     $contextMenu.css({
                       display: "block",
                       left: e.pageX,
                       top: e.pageY
                     });
                     return false;

                   });

                   $contextMenu.on("click", "a", function(e) {
                     e.preventDefault();
                     $contextMenu.removeClass("contextOpened");
                     $contextMenu.hide();
                   });

                   $('body').on('click', function() { //body 클릭하면 추가 꺼짐
                      $contextMenu.hide();
                      $contextMenu.removeClass("contextOpened");
                  });
                //newEvent(startDate, endDate);
               },
               eventClick: function(event, jsEvent, view) { //일정 클릭시
                editEvent(event);

               },
           expandRows : true, //화면에 맞게 높이 재설정
           themeSystem: 'bootstrap4',
           initialDate: new Date(),
           timezone: "local",
           nextDayThreshold: "09:00:00",
           allDaySlot: true,
           displayEventTime: true,
           displayEventEnd: true,
           firstDay: 1,
           weekNumbers: false,
           selectable: true,
           weekNumberCalculation: "ISO",
           eventLimit: true,
           eventLimitClick: 'week', //popover
           navLinks: true,
           timeFormat: 'HH:mm',
           defaultTimedEventDuration: '01:00:00',
           editable: false,
           slotLabelFormat: 'HH:mm',
           weekends: true,
           nowIndicator: true,
           dayPopoverFormat: 'dddd DD/MM',
           longPressDelay : 0,
           eventLongPressDelay : 0,
           selectLongPressDelay : 0,
          buttonText:{
            today: '오늘',
            month:  '월',
            week:   '주',
            day:    '일',
            list:   '목록'
          },
          themeButtonIcons:{
            prev: 'circle-triangle-w',
              next: 'circle-triangle-e',
              prevYear: 'seek-prev',
              nextYear: 'seek-next'
          },
       events: data //loadInitialCalendar에 담겨져서 넘어온 event값을 받는다
    });

         var miniCalendar = $('#MiniCalendar').fullCalendar({
            themeSystem: 'bootstrap4',
             header: { //캘린더 헤더
               left: '', //왼쪽
              center: 'title', //중간
              right: '' //오른쪽
           },
            initialDate: new Date(), //오늘 날짜 기준으로 초기설정
            defaultView: 'basicDay', //오늘 일정
            selectable: false,
            editable: false,
            events: data
         });
});

    $('#addCustomCalendar').click(function(){ //일정 생성 버튼을 누르면 newEvent 활성화
        const today = new Date();
        const today1 = new Date(today);
        today1.setDate(today.getDate()+1);

        newEvent(today,today1);
    });

    //일정 생성하기
       newEvent = function(start, end) {
           var colorEventyType;
           $("#contextMenu").hide();

           $('input#title').val("");
           $('#starts-at').val(moment(start).format("YYYY-MM-DD HH:mm"));
           $('#ends-at').val(moment(end).subtract(1,'d').format("YYYY-MM-DD HH:mm"));
           $('#address_kakao1').val("");
           $('#add-event-desc').val("");
           $('#verticalycentered').modal('show');
           var admin = $('#logType').val();
           var statusAllDay = $('[id=allday]').prop('checked');
           var endDay;

            if(admin != "관리팀" && admin != "인사팀" ){ //관리팀이나 인사팀이 아니면 선택불가
                $("select option[value='공지사항']").prop('disabled',true);
                $("select option[value='휴일']").prop('disabled',true);
            }

            $('.allDayNewEvent').on('change',function () {
                if ($(this).is(':checked')) {
                  statusAllDay = true;
                } else {
                  statusAllDay = false;
                }
              });

           $('#save-event').unbind();
           $('#save-event').on('click', function() {
               var title = $('input#title').val();
               var description = $('#add-event-desc').val();
               var startDay = moment($('#starts-at').val()).format("YYYY-MM-DD HH:mm");
               var endDay =  moment($('#ends-at').val()).format("YYYY-MM-DD HH:mm");
               var location = $('#address_kakao1').val();
               var type = $('#logType').val();
               var username = parseInt($('#logId').val());
               var backgroundColor = $('#bgcolor').val();
               var calendar = $('#calendar').val();
               if (confirm("일정생성 하시겠습니까?")) {
                 var eventData = {
                     title: title,
                     description: description,
                     start: startDay,
                     end: endDay,
                     location: location,
                     type: type,
                     username: username,
                     backgroundColor: backgroundColor,
                     calendar: calendar,
                     allDay: statusAllDay
                   };
                var events = new Array();
                events.push(eventData);

                var jsonData = JSON.stringify(events); //일정생성창에서 생성한 데이터를 json으로 바꾼후 ajax로 전달
                 $.ajax({
                        url: "/calendar/addEvent",
                        method: "POST",
                        dataType: "json",
                        cache : false,
                        contentType : "application/json; charset:UTF-8",
                        data: jsonData
                    })
                    .fail(function (request, status, error) {
                        document.location.href = document.location.href;
                    });
                   $('#verticalycentered').find('input, textarea').val('');
                   $('#verticalycentered').find('input:checkbox').prop('checked',false);
                   $('#ends-at').prop('disabled', false);
                   $('#verticalycentered').modal('hide');
                 }
               else {
                 alert("취소됬습니다.")
               }
           });
         }

         //일정 수정 등록
       editEvent = function(event, element, view) {
           $('.popover.fade.top').remove();
           $(element).popover("hide"); //메뉴창 숨김

           if(event.allDay == true){ //체크 되있으면
             $('#editEventModal').find('#editEndDate').attr("disabled", true);
             $('#editEventModal').find('#editEndDate').val("");
             $(".allDayEdit").prop('checked', true);
           }else{ //체크 안되있으면
             $('#editEventModal').find('#editEndDate').attr("disabled", false);
             $('#editEventModal').find('#editEndDate').val(moment(event.end).format("YYYY-MM-DD HH:mm"));
             $(".allDayEdit").prop('checked', false);
           }

           if(event.username == "0"){ //공지사항으로 작성됬으면 못지우게
                $('#editTitle').attr("readonly",true);
                $('#editStartDate').attr("readonly",true);
                $('#edit-event-desc').attr("readonly",true);
                $('#deleteEvent').css('display', 'none');
                $('#updateEvent').css('display', 'none');
           }else if(event.username != $('#logId').val()){ //클릭한 일정과 로그인한 계정 id가 다를경우 변경,삭제 버튼 숨기기
                $('#deleteEvent').css('display', 'none');
                $('#updateEvent').css('display', 'none');
           }else{
                $('#editTitle').attr("readonly",false);
                $('#editStartDate').attr("readonly",false);
                $('#edit-event-desc').attr("readonly",false);
                $('#deleteEvent').css('display', 'block');
                $('#updateEvent').css('display', 'block');
           }

           $('#editAllday').on('change',function () {
             if ($(this).is(':checked')) {
                 $('#editEventModal').find('#editEndDate').attr("disabled", true);
                 $('#editEventModal').find('#editEndDate').val("");
                 $(".allDayEdit").prop('checked', true);
               } else {
                 $('#editEventModal').find('#editEndDate').attr("disabled", false);
                 $('#editEventModal').find('#editEndDate').val(moment(event.end).format("YYYY-MM-DD HH:mm"));
                 $(".allDayEdit").prop('checked', false);
               }
           });

           $('#editTitle').val(event.title);
           $('#editStartDate').val(moment(event.start).format("YYYY-MM-DD HH:mm"));
           $('#editAddress_kakao1').val(event.location);
           $('#editCalendar-type').val(event.calendar).prop("selected",true);
           $('#edit-event-desc').val(event.description);
           $('#editBgcolor').val(event.backgroundColor);

           $('#editEventModal').modal('show');

           $('#updateEvent').unbind();
           $('#updateEvent').on('click', function() {
             var statusAllDay;
             if ($(".allDayEdit").is(':checked')) {
               statusAllDay = true;
             }else{
               statusAllDay = false;
             }
             var id = event.id;
             var title = $('#editTitle').val();
             var startDate = moment($('#editStartDate').val()).format("YYYY-MM-DD HH:mm");
             var calendar = $('#editCalendar-type').val();
             var description = $('#edit-event-desc').val();
             if($(".allDayEdit").is(':checked')){ //하루종일이 체크되있으면
               var endDate = null;
            }else{ //체크 안되있으면
               var endDate = moment($('#editEndDate').val()).format("YYYY-MM-DD HH:mm");
             }
               var location = $('#editAddress_kakao1').val();
               var type = event.type;
               var username = event.username;
               var backgroundColor = $('#editBgcolor').val();
             $('#editEventModal').modal('hide');
             if (confirm("일정변경 하시겠습니까?")) {
                  var editEventData = {
                      id : id,
                      title: title,
                      description: description,
                      start: startDate,
                       end: endDate,
                       location: location,
                       type: type,
                       username: username,
                       backgroundColor: backgroundColor,
                       calendar: calendar,
                       allDay: statusAllDay
                    };
                 var events = new Array();
                 events.push(editEventData);
                 var editData = JSON.stringify(events); //일정생성창에서 생성한 데이터를 json으로 바꾼후 ajax로 전달
                  $.ajax({
                         url: "/calendar/updateEvent",
                         method: "POST",
                         dataType: "json",
                         cache : false,
                         contentType : "application/json; charset:UTF-8",
                         data: editData
                     })
                     .fail(function (request, status, error) {
                         document.location.href = document.location.href;
                     });
                  }else {
                  alert("취소됬습니다.")
                }
           });

           $('#deleteEvent').on('click', function() {
            if (confirm("일정을 정말 삭제 하시겠습니까?")) {
             $('#deleteEvent').unbind();
             if (event._id.includes("_fc")){
               $("#MainCalendar").fullCalendar('removeEvents', [event._id]);
             } else {
               $("#MainCalendar").fullCalendar('removeEvents', [event._id]);
             }
             $('#editEventModal').modal('hide');
             }else{
                alert("취소됬습니다.");
             }
           });
         }


    $('.filter').on('change', function() {
        $('#MainCalendar').fullCalendar('rerenderEvents');

         });

         $("#calendar_filter").select2({
             placeholder: "종류를 선택해주세요",
             allowClear: true
         });


        //SET DEFAULT VIEW CALENDAR
        var defaultCalendarView = $("#calendar_view").val();

        if(defaultCalendarView == 'month'){
            $('#MainCalendar').fullCalendar( 'changeView', 'month');
        }else if(defaultCalendarView == 'agendaWeek'){
            $('#MainCalendar').fullCalendar( 'changeView', 'agendaWeek');
        }else if(defaultCalendarView == 'agendaDay'){
            $('#MainCalendar').fullCalendar( 'changeView', 'agendaDay');
        }else if(defaultCalendarView == 'listWeek'){
            $('#MainCalendar').fullCalendar( 'changeView', 'listWeek');
        }

        $('#calendar_view').on('change',function () {

          var defaultCalendarView = $("#calendar_view").val();
          $('#MainCalendar').fullCalendar('changeView', defaultCalendarView);

        });

        $('#closeEditModal').on('click', function() {
            $('#editEventModal').modal('hide');
            $('#editEventModal').hide();
        });
  });


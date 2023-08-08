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
      alert(JSON.stringify(data));
      var calendar = $('#MainCalendar').fullCalendar({
        eventRender: function(event, element, view) { //일정만들기
         var startTimeEventInfo = moment(event.start).format('HH:mm'); //시작시간 포맷
         var endTimeEventInfo = moment(event.end).format('HH:mm'); //종료시간 포맷
         var startDateEventInfo = moment(event.start).format('YYYY/MM/DD'); //시작시간 포맷
         var endDateEventInfo = moment(event.end).format('YYYY/MM/DD'); //종료시간 포맷 자꾸 끝나는일이 +1되서 나와서 subtract해서 -1일
         var displayEventDate; //보여지는 시간

         if(event.allDay == false){ //프로젝트 체크가 안되있을때
           displayEventDate = startDateEventInfo+" "+startTimeEventInfo + " - " + endDateEventInfo+" "+endTimeEventInfo; //시작시간-끝나는시간
         }else{
           displayEventDate = startDateEventInfo; //시작날짜만
         }

          element.popover({ //마우스 hover할때 나오는 팝업
            title:    '<div class="popoverTitleCalendar">'+ event.title +'</div>',
            content:  '<div class="popoverInfoCalendar">' +
                      '<p><strong>종류 :</strong> ' + event.calendar + '</p>' +
                      '<p><strong>작성자 :</strong> ' + event.username + '</p>' +
                      '<p><strong>위치 :</strong> '+ "한국" + '</p>' +
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

           /*if (event.username == "Caio Vitorelli") { //일정 색상 설정
               element.css('background-color', '#f4516c');
           }
           if (event.username == "Peter Grant") {
               element.css('background-color', '#1756ff');
           }
           if (event.username == "Adam Rackham") {
               element.css('background-color', '#9816f4');
           }*/

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
});
        $('#addCustomCalendar').click(function(){ //일정생성 시 모달에 있는 값 초기화
            $('input#title').val("");
            $('#starts-at').val("");
            $('#ends-at').val("");
            $('#address_kakao1').val("");
            $('#add-event-desc').val("");
        });


    //일정 생성하기
       newEvent = function(start, end) {
           var colorEventyType;
           $("#contextMenu").hide();
           $('input#title').val("");
           $('#starts-at').val(start);
           $('#ends-at').val(end);
           $('#address_kakao1').val("");
           $('#add-event-desc').val("");
           $('#verticalycentered').modal('show');

           var statusAllDay;
           var endDay;

           $('.allDayNewEvent').on('change',function () {

             if ($(this).is(':checked')) {
               statusAllDay = true;
               var endDay = $('#ends-at').prop('disabled', true);
             } else {
               statusAllDay = false;
               var endDay = $('#ends-at').prop('disabled', false);
             }
           });

           $('#save-event').unbind();
           $('#save-event').on('click', function() {
           var title = $('input#title').val();
           var startDay = $('#starts-at').val();
           if(!$(".allDayNewEvent").is(':checked')){
             var endDay = $('#ends-at').val();
           }
           var calendar = $('#calendar-type').val();
           var description = $('#add-event-desc').val();
           var type = eventType;
           if (title) {
             var eventData = {
                 _id: eventId,
                 title: title,
                 start: startDay,
                 end: endDay,
                 description: description,
                 type: type,
                 calendar: calendar,
                 className: colorEventyType,
                 username: 'Caio Vitorelli',
                 backgroundColor: '#1756ff',
                 textColor: '#ffffff',
                 allDay: statusAllDay
             };
             $("#MainCalendar").fullCalendar('renderEvent', eventData, true);
             $('#verticalycentered').find('input, textarea').val('');
             $('#verticalycentered').find('input:checkbox').prop('checked',false);
             $('#ends-at').prop('disabled', false);
             $('#verticalycentered').modal('hide');
             }
           else {
             alert("Title can't be blank. Please try again.")
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

           $('.allDayEdit').on('change',function () {
             if ($(this).is(':checked')) {
                 $('#editEventModal').find('#editEndDate').attr("disabled", true);
                 $('#editEventModal').find('#editEndDate').val("");
                 $(".allDayEdit").prop('checked', true);
               } else {
                 $('#editEventModal').find('#editEndDate').attr("disabled", false);
                 $(".allDayEdit").prop('checked', false);
               }
           });

           $('#editTitle').val(event.title);
           $('#editStartDate').val(moment(event.start).format("YYYY-MM-DD HH:mm"));
           $('#edit-calendar-type').val(event.calendar);
           $('#editAddress_kakao1').val(event.location);
           $('#edit-event-desc').val(event.description);
           $('.eventName').text(event.title);
           $('#editEventModal').modal('show');
           $('#updateEvent').unbind();
           $('#updateEvent').on('click', function() {
             var statusAllDay;
             if ($(".allDayEdit").is(':checked')) {
               statusAllDay = true;
             }else{
               statusAllDay = false;
             }
             var title = $('input#editTitle').val();
             var startDate = $('input#editStartDate').val();
             var endDate = $('input#editEndDate').val();
             var calendar = $('#edit-calendar-type').val();
             var description = $('#edit-event-desc').val();
             $('#editEventModal').modal('hide');
             var eventData;
             if (title) {
               event.title = title
               event.start = startDate
               event.end = endDate
               event.calendar = calendar
               event.description = description
               event.allDay = statusAllDay
               $("#MainCalendar").fullCalendar('updateEvent', event);
             } else {
             alert("Title can't be blank. Please try again.")
             }
           });

           $('#deleteEvent').on('click', function() {
             $('#deleteEvent').unbind();
             if (event._id.includes("_fc")){
               $("#MainCalendar").fullCalendar('removeEvents', [event._id]);
             } else {
               $("#MainCalendar").fullCalendar('removeEvents', [event._id]);
             }
             $('#editEventModal').modal('hide');
           });
         }


    $('.filter').on('change', function() {
        $('#MainCalendar').fullCalendar('rerenderEvents');
         });

         $("#calendar_filter").select2({
             placeholder: "종류를 선택해주세요",
             allowClear: true
         });

        //var minDate = moment().subtract(0, 'days').millisecond(0).second(0).minute(0).hour(0);

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

  });


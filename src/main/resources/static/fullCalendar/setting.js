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
  }

var newEvent;
var editEvent;

// 캘린더 초기설정
$(document).ready(function() {
      var calendar = $('#MainCalendar').fullCalendar({
        eventRender: function(event, element, view) { //일정만들기

                 var startTimeEventInfo = moment(event.start).format('HH:mm'); //시작시간 포맷
                 var endTimeEventInfo = moment(event.end).format('HH:mm'); //종료시간 포맷
                 var startDateEventInfo = moment(event.start).format('YYYY/MM/DD'); //시작시간 포맷
                 var endDateEventInfo = moment(event.end).format('YYYY/MM/DD'); //종료시간 포맷
                 var displayEventDate; //보여지는 시간

                 if(event.allDay == false){ //프로젝트 체크가 안되있을때
                   displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo; //시작시간-끝나는시간
                 }else{
                   displayEventDate = startDateEventInfo + " - " + endDateEventInfo; //시작날짜-끝나는날짜
                 }

                  element.popover({ //마우스 hover할때 나오는 팝업
                    title:    '<div class="popoverTitleCalendar">'+ event.title +'</div>',
                    content:  '<div class="popoverInfoCalendar">' +
                              '<p><strong>Calendar:</strong> ' + event.calendar + '</p>' +
                              '<p><strong>Username:</strong> ' + event.username + '</p>' +
                              '<p><strong>Event Type:</strong> ' + event.type + '</p>' +
                              '<p><strong>Event Time:</strong> ' + displayEventDate + '</p>' +
                              '<div class="popoverDescCalendar"><strong>Description:</strong> '+ event.description +'</div>' +
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
               header: {
                          left: ' prevYear, nextYear, today',
                         center: 'prev, title, next',
                         right: 'month,agendaWeek,agendaDay,listWeek'
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
                      eventAfterAllRender: function(view) {
                          if(view.name == "month"){
                             $(".fc-content").css('height','auto');
                             $(".fc-content").css('padding','8px 20px 8px 22px');
                           }
                      },
                      eventLimitClick: function(cellInfo, event) {


                      },
                      eventResize: function(event, delta, revertFunc, jsEvent, ui, view) {
                           $('.popover.fade.top').remove();
                      },
                      eventDragStart: function(event, jsEvent, ui, view) {
                           var draggedEventIsAllDay;
                           draggedEventIsAllDay = event.allDay;
                      },
                      eventDrop: function(event, delta, revertFunc, jsEvent, ui, view) {
                           $('.popover.fade.top').remove();
                      },
                      unselect: function(jsEvent, view) {
                         //$(".dropNewEvent").hide();
                      },
                      dayClick: function(startDate, jsEvent, view) {

                        //var today = moment();
                        //var startDate;

                        //if(view.name == "month"){

                        //  startDate.set({ hours: today.hours(), minute: today.minutes() });
                        //  alert('Clicked on: ' + startDate.format());

                        //}

                      },
                      select: function(startDate, endDate, jsEvent, view) {

                        var today = moment();
                        var startDate;
                        var endDate;

                        if(view.name == "month"){
                           startDate.set({ hours: today.hours(), minute: today.minutes() });
                           startDate = moment(startDate).format('ddd DD MMM YYYY HH:mm');
                           endDate = moment(endDate).subtract('days', 1);
                           endDate.set({ hours: today.hours() + 1, minute: today.minutes() });
                           endDate = moment(endDate).format('ddd DD MMM YYYY HH:mm');
                        }else{
                           startDate = moment(startDate).format('ddd DD MMM YYYY HH:mm');
                           endDate = moment(endDate).format('ddd DD MMM YYYY HH:mm');
                        }

                        var $contextMenu = $("#contextMenu");

                        var HTMLContent = '<ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu" style="display:block;position:static;margin-bottom:5px;">' +
                     '<li onclick=\'newEvent("'+ startDate +'","'+ endDate +'","'+ "일정" +'")\'> <a tabindex="-1" href="#">일정 추가</a></li>' +
                     '<li onclick=\'newEvent("'+ startDate +'","'+ endDate +'","'+ "프로젝트" +'")\'> <a tabindex="-1" href="#">프로젝트 추가</a></li>' +
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

                           $('body').on('click', function() {
                              $contextMenu.hide();
                              $contextMenu.removeClass("contextOpened");
                          });

                        //newEvent(startDate, endDate);

                       },
                       eventClick: function(event, jsEvent, view) {

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
       editable: true,
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
       events: [{
           title: '제목1',
           description: '설명1',
           start: '2023-08-07T09:30',
           end: '2023-08-07T10:00',
           category: 'Appointment',
           calendar: 'Sales',
           className: 'colorAppointment',
           username: '김',
           backgroundColor: "#f4516c",
           textColor: "#ffffff",
           allDay: false
       }, {
           title: 'California Polytechnic',
           description: 'Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.',
           start: '2023-08-01T12:30',
           end: '2023-08-01T15:30',
           category: 'Appointment',
           calendar: 'Sales',
           className: 'colorAppointment',
           username: 'Adam Rackham',
           backgroundColor: "#9816f4",
           textColor: "#ffffff",
           allDay: false
       }, {
           title: 'Vermont University 2',
           description: 'Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.',
           start: '2023-08-02',
           end: '2023-08-02',
           category: 'Check-in',
           calendar: 'Sales',
           className: 'colorCheck-in',
           username: 'Adam Rackham',
           backgroundColor: "#9816f4",
           textColor: "#ffffff",
           allDay: true
       }, {
           title: 'Vermont University',
           description: 'Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.',
           start: '2023-08-06',
           end: '2023-08-06',
           category: 'Checkout',
           calendar: 'Sales',
           className: 'colorCheckout',
           username: 'Peter Grant',
           backgroundColor: "#1756ff",
           textColor: "#ffffff",
           allDay: true
       }, {
           title: 'Michigan High School',
           description: 'Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.',
           start: '2023-08-08',
           end: '2023-08-08',
           category: 'Inventory',
           calendar: 'Lettings',
           className: 'colorInventory',
           username: 'Peter Grant',
           backgroundColor: "#1756ff",
           textColor: "#ffffff",
           allDay: true
       }, {
           title: 'Vermont High School',
           description: 'Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.',
           start: '2023-08-09',
           end: '2023-08-09',
           category: 'Valuation',
           calendar: 'Lettings',
           className: 'colorValuation',
           username: 'Peter Grant',
           backgroundColor: "#1756ff",
           textColor: "#ffffff",
           allDay: true
       }, {
           title: 'California High School',
           description: 'Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.',
           start: '2023-08-07',
           end: '2023-08-07',
           category: 'Viewing',
           calendar: 'Lettings',
           className: 'colorViewing',
           username: 'Caio Vitorelli',
           backgroundColor: "#f4516c",
           textColor: "#ffffff",
           allDay: true
       }]

    });

    $('.filter').on('change', function() {
        $('#MainCalendar').fullCalendar('rerenderEvents');
         });

         $("#type_filter").select2({
             placeholder: "Filter Types",
             allowClear: true
         });

         $("#calendar_filter").select2({
             placeholder: "Filter Calendars",
             allowClear: true
         });

        $("#starts-at, #ends-at").datetimepicker({
          step: 30,
          format: 'ddd DD MMM YYYY HH:mm'
        });

        //var minDate = moment().subtract(0, 'days').millisecond(0).second(0).minute(0).hour(0);

        $(" #editStartDate, #editEndDate").datetimepicker({
          step: 30,
          format: 'ddd DD MMM YYYY HH:mm'
          //minDate: minDate
        });

        //CREATE NEW EVENT CALENDAR

        newEvent = function(start, end, eventType) {
            var colorEventyType;

            if (eventType == "일정"){
              colorEventyType = "colorAppointment";
            }
            else if (eventType == "프로젝트"){
              colorEventyType = "colorCheck-in";
            }

            $("#contextMenu").hide();
            $('.eventType').text(eventType);
            $('input#title').val("");
            $('#starts-at').val(start);
            $('#ends-at').val(end);
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

            //GENERATE RAMDON ID - JUST FOR TEST - DELETE IT
            var eventId = 1 + Math.floor(Math.random() * 1000);
            //GENERATE RAMDON ID - JUST FOR TEST - DELETE IT

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

        //EDIT EVENT CALENDAR

          editEvent = function(event, element, view) {

              $('.popover.fade.top').remove();
              $(element).popover("hide");

              //$(".dropdown").hide().css("visibility", "hidden");

              if(event.allDay == true){
                $('#editEventModal').find('#editEndDate').attr("disabled", true);
                $('#editEventModal').find('#editEndDate').val("");
                $(".allDayEdit").prop('checked', true);
              }else{
                $('#editEventModal').find('#editEndDate').attr("disabled", false);
                $('#editEventModal').find('#editEndDate').val(event.end.format('ddd DD MMM YYYY HH:mm'));
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
              $('#editStartDate').val(event.start.format('ddd DD MMM YYYY HH:mm'));
              $('#edit-calendar-type').val(event.calendar);
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

        //SET MIN TIME AGENDA

        $('#calendar_start_time').on('change',function () {

          var minTimeAgendaView = $(this).val();
          $('#MainCalendar').fullCalendar('option', {minTime: minTimeAgendaView});

        });

        //SET MAX TIME AGENDA

        $('#calendar_end_time').on('change',function () {

          var maxTimeAgendaView = $(this).val();
          $('#MainCalendar').fullCalendar('option', {maxTime: maxTimeAgendaView});

        });

        //SHOW - HIDE WEEKENDS

        var activeInactiveWeekends = false;
        checkCalendarWeekends();

        $('.showHideWeekend').on('change',function () {
          checkCalendarWeekends();
        });

        function checkCalendarWeekends(){

          if ($('.showHideWeekend').is(':checked')) {
            activeInactiveWeekends = true;
            $('#MainCalendar').fullCalendar('option', {
              weekends: activeInactiveWeekends
            });
          } else {
            activeInactiveWeekends = false;
            $('#MainCalendar').fullCalendar('option', {
              weekends: activeInactiveWeekends
            });
          }

        }

        //일정 생성
  });


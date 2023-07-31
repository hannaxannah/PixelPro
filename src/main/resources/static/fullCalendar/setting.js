// 캘린더 초기설정

 document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
      locale: 'ko', //언어는 한국어
      initialDate: new Date(), //오늘 날짜 기준으로 초기설정
      initialView: 'dayGridMonth', //처음 보기 기준은 달
      nowIndicator: true,
      headerToolbar: {
        left: 'prevYear,prev,next,nextYear today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
      },
      buttonText:{
        today: '오늘',
        month:  '월',
        week:   '주',
        day:    '일',
        list:   '목록'
      },
      navLinks: true, // can click day/week names to navigate views
      editable: true, //
      selectable: true, //클릭이랑 드래그 설정
      selectMirror: true, //드래그해서 옮길때 영역 표시
      dayMaxEvents: true, // allow "more" link when too many events
      events: [
        {
          title: '하루종일',
          start: '2023-08-01',
        },
        {
          title: '범위 표시',
          start: '2023-08-07',
          end: '2023-08-10'
        },
        {
          groupId: 999,
          title: '시간 표시1',
          start: '2023-08-09T16:00:00'
        },
        {
          groupId: 999,
          title: '시간 표시2',
          start: '2023-08-16T16:00:00'
        },
        {
          title: '날짜+시간',
          start: '2023-08-12T10:30:00',
          end: '2023-08-12T12:30:00'
        },
        {
          title: '시간-시작만 1시간 단위',
          start: '2023-08-12T12:00:00'
        },
        {
          title: '30분단위 범위는 1시간',
          start: '2023-08-12T14:30:00'
        },
        {
          title: '클릭하면 네이버로',
          url: 'http://naver.com/',
          start: '2023-08-28'
        }
      ]
    });
    calendar.setOption('locale', 'ko'); //언어 한국어설정
    calendar.render(); //캘린더 구현
  });
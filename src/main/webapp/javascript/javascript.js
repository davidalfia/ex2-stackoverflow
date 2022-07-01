'use strict';

(function () {

    let questions = null;

    function status(response) {
        if (response.status >= 200 && response.status < 300) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(new Error(response.status));
        }
    }


    function toHtml(json){
        let str = '';
        str+=`<div class="container">`
        json.questionStack.forEach(obj=> {
            str+=`<div>${obj.question} </div>`;
            str+= `<div>${obj.answersNumber} answers</div>`;
            str+=`<form action="/AddAnswerServlet" method="get">
                        <input type="hidden" name="questionNumber" value="${obj.key}">
                        <input type="submit" value="answer">
                   </form>`;
            str+=`<button value="${obj.key}">answer</button>`;
            str+=`<br/>`
        })
        str+=`</div>`
        return str;
    }


    function fetchLandingPage()
    {
        fetch('/QuestionsServlet')
            .then(status)
            .then(res=> res.json())
            .then(json=>{
                questions.innerHTML =  toHtml(json);
            })
            .catch(()=>{

            })
    }

    document.addEventListener("DOMContentLoaded",function (){

        questions = document.getElementById("questions");

        fetchLandingPage();

        document.addEventListener('click', function (e) { // button click listener

            if(e.target.tagName === "BUTTON"){ // only if target is button
                console.log("click")
            }
        }, false);


        })


})();
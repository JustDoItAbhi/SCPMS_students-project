import { Button, Tabs } from "antd";
import { DeleteAllTeachersTopicByTeacherId, GetALLTopicsByTeacherid} from "../apis";
import { useEffect, useState } from "react";
import TopicPage from "../TOPICS/TopicPage";


function DeleteAllTopics(){
const[topic,setTopic]=useState([]);


useEffect(()=>{
const teacherId=localStorage.getItem("teacherId");
   const GetTopics=async()=>{
        try{
            const isTopic=await GetALLTopicsByTeacherid(teacherId);
            console.log(" ALL TOPICS ",isTopic);
            setTopic(isTopic);

        }catch(err){
            console.log(err.message);
        }
    }
})

const ListForm=[
{
    key:"1",
    label:"TOPICS",
    children: <TopicPage/>
}
]

    return(
<div>
  {topic && topic.length > 0 ? (
    topic.map((item, index) => (
      <h2 key={index}>{item.topic}</h2>
    ))
  ) : (
    <h2>TOPIC NOT AVAILABLE</h2>
  )}

  <Tabs defaultActiveKey="1" items={ListForm} />
</div>
    )
}
export default DeleteAllTopics;
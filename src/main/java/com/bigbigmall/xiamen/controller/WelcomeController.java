package com.bigbigmall.xiamen.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @auther 7343 
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

        @RequestMapping("/")
        @ResponseBody
        void index(HttpServletResponse response) throws ParserConfigurationException, TransformerConfigurationException, IOException, TransformerException {
                //创建文档
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                //根元素
                Element documentElement = document.createElement("document");
                documentElement.setAttribute("user", "me");
                Element stories = document.createElement("stories");
                stories.setAttribute("status", "200");
                //第一个故事
                Element story1 = document.createElement("story");
                Element comments = document.createElement("comments");
                Element comment = document.createElement("comment");
                Element id = document.createElement("id");
                id.setTextContent("defad05a-0eb1-4545-b4aa-39460f174fbc");
                Element content = document.createElement("content");
                content.setTextContent("這是一篇好文章阿");
                Element who = document.createElement("who");
                Element nickname = document.createElement("nickname");
                Element pcid = document.createElement("id");
                pcid.setTextContent("7f84cc84-bb50-4cdf-8578-ef2827d35726");
                nickname.setTextContent("pc");

                who.appendChild(nickname);
                who.appendChild(pcid);
                comment.appendChild(id);
                comment.appendChild(content);
                comment.appendChild(who);
                //第一个故事的comment2
                Element comment2 = document.createElement("comment");
                Element id2 = document.createElement("id");
                id2.setTextContent("8ee5d256-a4e9-4c67-a5d0-0699d9b0cbd7");
                Element content2 = document.createElement("content");
                content2.setTextContent("家惠什麼時候要開始減肥呀");
                Element who2 = document.createElement("who");
                Element nickname2 = document.createElement("nickname");
                nickname2.setTextContent("pc");
                Element id3 = document.createElement("id");
                id3.setTextContent("7f84cc84-bb50-4cdf-8578-ef2827d35726");
                who2.appendChild(nickname2);
                who2.appendChild(id3);
                comment2.appendChild(who2);
                comment2.appendChild(content2);

                Element emotions = document.createElement("emotions");
                emotions.setTextContent("0");
                Element postedAt = document.createElement("postedAt");
                postedAt.setTextContent("2014-02-11T00:00:00+08:00");
                Element author = document.createElement("author");
                Element nickname1 = document.createElement("nickname");
                nickname1.setTextContent("redan");
                Element authorId = document.createElement("id");
                authorId.setTextContent("e4b6a337-6647-4521-ac9a-c4e0a3853626");
                author.appendChild(nickname1);
                author.appendChild(authorId);
                comments.appendChild(comment);
                comments.appendChild(comment2);
                story1.appendChild(comments);
                story1.appendChild(emotions);
                story1.appendChild(postedAt);
                story1.appendChild(author);
                Element story2 = document.createElement("story");
                Element idT = document.createElement("id");
                idT.setTextContent("190cc757-7a0e-4712-97c9-bf5f77fd5a8e");
                Element contentT = document.createElement("content");
                contentT.setTextContent("this is content 這是一篇文章");
                story1.appendChild(idT);
                story1.appendChild(contentT);

                Element story3 = document.createElement("story");
                //追加各层的子节点

                //第二个故事
                Element emotions2 = document.createElement("emotions");
                emotions2.setTextContent("0");
                Element postedAt2 = document.createElement("postedAt");
                postedAt2.setTextContent(">2019-01-30 03:08:30.556067");
                Element author3 = document.createElement("author");
                Element hid = document.createElement("id");
                hid.setTextContent("fdb53388-243b-11e9-b2d7-23d188ab349e");
                Element nickname3 = document.createElement("nickname");
                nickname3.setTextContent("阿惠");
                author3.appendChild(hid);
                author3.appendChild(nickname3);
                story2.appendChild(emotions2);

                story2.appendChild(postedAt2);
                story2.appendChild(author3);
                Element idL = document.createElement("id");
                idL.setTextContent("72ff2337-1b0b-473c-8120-6ff028632806");
                Element contentL = document.createElement("content");
                contentL.setTextContent("路，就是一條直直的。認同請按讚，不認同請分享");
                story2.appendChild(idL);
                story2.appendChild(contentL);

                //第三故事
                Element emotions3 = document.createElement("emotions");
                emotions3.setTextContent("0");
                Element postedAt3 = document.createElement("postedAt");
                postedAt3.setTextContent("2019-01-30 03:08:54.812136");

                Element author4 = document.createElement("author");

                Element nickname4 = document.createElement("nickname");
                nickname4.setTextContent("生仔");
                Element id4 = document.createElement("id");
                id4.setTextContent("f45f0aa2-243b-11e9-b2d7-7339b94259f1");
                author4.appendChild(nickname4);
                author4.appendChild(id4);
                Element id5 = document.createElement("id");
                id5.setTextContent("5bc82c97-14e7-44ad-91ea-8941a12eae43");
                Element content5 = document.createElement("content");
                content5.setTextContent("累、累、累！");
                story3.appendChild(emotions3);
                story3.appendChild(postedAt3);
                story3.appendChild(author4);
                story3.appendChild(author4);

                story3.appendChild(id5);

                story3.appendChild(content5);

                stories.appendChild(story1);
                stories.appendChild(story2);

                stories.appendChild(story3);
                documentElement.appendChild(stories);
                document.appendChild(documentElement);
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(document), new StreamResult(response.getOutputStream()));
        }
        @RequestMapping("/Dom")
        @ResponseBody
         void index2(HttpServletResponse response) throws ParserConfigurationException, TransformerConfigurationException, IOException, TransformerException {
              
                //创建的文档解析器
                Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                //创建根节点
                Element documentElement = newDocument.createElement("document");
                //文档追加根节点
                newDocument.appendChild(documentElement);

                //创建客户端执行get请求
                CloseableHttpResponse response1 = HttpClients.createDefault().execute(new HttpGet("https://redan-api.herokuapp.com/"));
                //请求过后返回数据
                HttpEntity entity = response1.getEntity();
                //如果的响应数据不为空的情况的,执行
                if (null != entity) {
                        //对响应数据进行编码格式的设置使用数据实体的工具
                        String str = EntityUtils.toString(entity, "UTF-8");
                        //针对以下的实体进行操作,拿到json数据之后进行一下的
                        JSONObject jsonObject = new JSONObject(str).getJSONObject("_links");
                        //遍历json对象
                    
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                                    
                                String next1 = keys.next();
                                Element createElement = newDocument.createElement(next1);
                                JSONObject jsonObject1 = jsonObject.getJSONObject(next1);
                                System.out.println("第一层的所有的key\t" + next1 + "第一层的所有值\t" + jsonObject1);
                                Iterator<String> keys1 = jsonObject1.keys();
                                while (keys1.hasNext()) {
                                        String next2 = keys1.next();
                                        Element createElement1 = newDocument.createElement(next2);
                                        String get = jsonObject1.get(next2).toString();
                                        createElement1.setTextContent(get);
                                        System.out.println("第二次的所有的key\t"+next2+"\t第二层的\t"+get);
                                       createElement.appendChild(createElement1);
                                }
                                  documentElement.appendChild(createElement);
                        }
                      

                }

              

           
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(newDocument), new StreamResult(response.getOutputStream()));
        }
         @RequestMapping("/DomViwe")
        @ResponseBody
         void index3(HttpServletResponse response) throws ParserConfigurationException, TransformerConfigurationException, IOException, TransformerException {
              
            
                //创建的文档解析器
                Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                //创建根节点
                Element documentElement = newDocument.createElement("document");
                //文档追加根节点
                newDocument.appendChild(documentElement);

                //创建客户端执行get请求
                CloseableHttpResponse response1 = HttpClients.createDefault().execute(new HttpGet("https://redan-api.herokuapp.com/profile/emotions"));
                //请求过后返回数据
                HttpEntity entity = response1.getEntity();
                if (null != entity) {
                        //指定编码格式
                        String string = EntityUtils.toString(entity, "UTF-8");
                        JSONObject jsonObject = new JSONObject(string).getJSONObject("alps");
                        //获取的jsonObject的所有key
                        Set<String> keySet = jsonObject.keySet();
                        Iterator<String> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                                String next = iterator.next();
                                String toString = jsonObject.get(next).toString();
                                //创建标签元素
                                Element createElement = newDocument.createElement(next);
                                //这是alps的两个子标签

                                System.out.println("alp的两个子标签\t" + next + "\talp的两个子标签的值\t" + toString);
                                //是文本的情况下,直接创建标签和追加文本值
                                if (toString.equals("version")) {
                                        String toString1 = jsonObject.get(next).toString();
                                        createElement.setTextContent(toString1);
                                } //是json数组的情况下
                                else if (!toString.equals("version")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("descriptor");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                //拿到所有json数组元素
                                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                //所有的key的集合
                                                Iterator<String> keys = jsonObject1.keys();
                                                while (keys.hasNext()) {
                                                        String next1 = keys.next();
                                                        String toString1 = jsonObject1.get(next1).toString();

                                                        //创建标签元素
                                                        Element createElement1 = newDocument.createElement(next1);
                                                        //createElement1.setTextContent(toString1);

                                                        System.out.println("拿到第二层的所有\t" + next1 + "\t拿到第二层的所有\t" + toString1);
                                                        //在第二层里面是文本的就直接添加文本
                                                        if (!next1.equals("descriptor")) {
                                                                //descriptor直接追究descriptor里面的键值对
                                                                createElement1.setTextContent(toString1);
                                                                System.out.println("没有到这里&&&&&&&&&&&&&&&&&&&在第二层里面是文本的&&&&&&&&&&&&&&&&&&&");
                                                        }
                                                        if (next1.equals("descriptor")) {

                                                                JSONArray jsonArray1 = jsonObject1.getJSONArray("descriptor");

                                                                for (int j = 0; j < jsonArray1.length(); j++) {

                                                                        JSONObject jsonObject2 = jsonArray1.getJSONObject(i);
                                                                        Iterator<String> keys1 = jsonObject2.keys();
                                                                        while (keys1.hasNext()) {
                                                                                String next2 = keys1.next();
                                                                                String string1 = jsonObject2.get(next2).toString();
                                                                                //创建节点
                                                                                Element createElement2 = newDocument.createElement(next2);

                                                                                System.out.println("拿到第三**********************层的所有\t" + next2 + "\t拿到第三********************层的所有\t" + string1);
                                                                                //在第四层是文本的情况
                                                                                if (!next2.equals("doc")) {
                                                                                        createElement2.setTextContent(string1);
                                                                                        System.out.println("第四层的是文本的情况");

                                                                                } else if (next2.equals("doc")) {
                                                                                        JSONObject jsonObject3 = jsonObject2.getJSONObject("doc");
                                                                                        Iterator<String> keys2 = jsonObject3.keys();
                                                                                        while (keys2.hasNext()) {
                                                                                                String next3 = keys2.next();
                                                                                                String string2 = jsonObject3.getString(next3);
                                                                                                Element createElement3 = newDocument.createElement(next3);
                                                                                                createElement3.setTextContent(string2);
                                                                                                System.out.println("第四层的######key\t" + next3 + "\t第四层的######值\t" + string2);
                                                                                                createElement2.appendChild(createElement3);
                                                                                        }

                                                                                }
                                                                                //追加节点
                                                                                createElement1.appendChild(createElement2);
                                                                        }
                                                                }
                                                        }
                                                        createElement.appendChild(createElement1);

                                                }

                                        }
                                }

                                documentElement.appendChild(createElement);
                        }

                }

           
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(newDocument), new StreamResult(response.getOutputStream()));
        }
}

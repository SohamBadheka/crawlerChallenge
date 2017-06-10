# crawlerChallenge
Problem : 
Given any page (URL), be able to classify the page, and return a list of relevant topics.

Input:
Any URL (e.g. http://sohambadheka.in, https://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster)
 
Output:
List of common topics that best describe the contents of that page
(e.g. data, work, web)

# External Libraries Used
- Used Jsoup for parsing the HTML page from the specific URL. (https://jsoup.org/)
- Added it to the maven dependency and built the project using it.

# Solution Explanation
1. Search Engines crawlers often parse the document(web page) in such a way that tells what are the keywords,
   that describe the page. For this, the main focus of the crawler program is to parse the h1 and the first
   p tag content. Crawler expects this to be SEO rich, so that the keywords that appear in this section is very
   important.
2. But this is not the only thing, we have to also scrap the whole page and check what are the frequent terms on the
   page and then sort them to get the top results/frequencies on the page. 
3. Then, compare these top results over h1 and 1st p tags.


# Approach to solve the Problem
1. App.java takes URL from the user argument and checks the validity of the URL whether it is fully qualified URL to be
   Parsed. Fetch h1, first p and overall content from the HTML page. 
2. Remove punctuations from the words and then apply preprocessing on those words that removes the common words(Stopwords). 
   To perform this operation, I have put the "stopwords_en.txt" in the source which is downloaded from google site
   "https://sites.google.com/site/kevinbouge/stopwords-lists".
3. After performing this, we have to keep track of each word and its frequency that word. This helps to understand which are 
   the frequent words that is important for our keyword-extraction.
 
   (We can also add another filtering by Stemming process which ignores two words having same root. E.G. Like, likable, liked
    liking are the same. So rather consider them all as one word)

4. After getting the top results of these words, we check whether these words really have enough importance for the page, which
   can be cross-checked by looking into h1 and p tags. If these frequent words are a part of h1 or the first p then
   add it to our resultArray that is the final output.
5. For example, 
  "https://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster" gives us the output array 
   [toaster,   bread, toast, cuisinart].

# DataStructure Used
- There are various datastructure used because of its strengths.
- Mainly, used HashMap for storing and managing the words & its frequency. And also used TreeMap/Comparators to sort the HashMap.
- ArrayList is used to maintain only the top results from the HashMap and compare them over h1, p (content)strings.

# Improvements

- Here, I only took one approach to crawl the page and provide important information about it. However, we can also put 
  different approaches to the page that handles spam words, considering title of the webpage, meta etc etc.
- We can also implement tfIdf and get the vector value of the document to understand the importance of the document over a 
  keyword. Here's another link to handle tfidf for the document, I have worked a few months ago. 
  https://github.com/SohamBadheka/DocumentSimilarity


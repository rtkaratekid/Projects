
# coding: utf-8

# In[1]:


import pandas as pd
import seaborn as sns
import numpy as np
import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')
sns.set_style('whitegrid')


# In[2]:


athlete = pd.read_csv('athlete_events.csv')
print(athlete.head(10))
print('\n')
print(athlete.info())
#Can we predict what type of athletes (or countries maybe), or find any patters related to medaling vs not?
#Can we show a breakdown of how countries have fared over time in the olympic games?
#Did the years that sport-enhancing drugs came into play show a difference?


# In[3]:


#drop athletes who didn't earn a medal
df_medals = athlete.dropna(subset=['Medal']) 

#split into summer and winter olympics
summer_medals = df_medals.drop(df_medals[df_medals['Season']=='Winter'].index)
winter_medals = df_medals.drop(df_medals[df_medals['Season']=='Summer'].index)


# In[4]:


summer_medals['Team'].nunique()


# In[6]:


summer_medals.head()


# In[14]:


summer_medals['Event'].nunique()


# In[ ]:


summer_medals['Event'].unique()


# In[ ]:


winter_medals['Event'].nunique()


# In[ ]:


winter_medals['Event'].unique()


# In[ ]:


winter_medals['Team'].nunique()


# In[ ]:


winter_medals['Team'].unique()


# In[ ]:


sns.pairplot(summer_medals.dropna(), hue='Sex', kind = 'reg')


# In[15]:


#A test on two countries
df_fin = summer_medals[summer_medals['Team'] == 'Finland']
df_usa = summer_medals[summer_medals['Team'] == 'United States']


# In[16]:


plt.figure(figsize=(40,15))
sns.swarmplot(x='Year', y='Height', data=df_fin, size=15)


# In[18]:


plt.figure(figsize=(40,15))
sns.swarmplot(x='Year', y='Height', data=df_usa)


# In[19]:


#Drop all the athletes who didn't earn medals for the USA in the Olympics
df_usa_medals = df_usa.dropna(subset=['Medal']) 

#Group athletes by year and count them
usa_groupby_year = df_usa_medals.groupby('Year')
usa_medal_count = usa_groupby_year.count()

#reset the index to count "Year" as a real column
#Chart the number of Medals won during each year of the olympics
sns.lmplot(x='Year', y='Medal',data=usa_medal_count.reset_index(), size=10)


# In[ ]:


#repeat for Finland
df_fin_medals = df_fin.dropna(subset=['Medal']) 
fin_groupby_year = df_fin_medals.groupby('Year')
fin_medal_count = fin_groupby_year.count()
sns.lmplot(x='Year', y='Medal',data=fin_medal_count.reset_index(), size=10)


# In[ ]:


#Now Winter
winter_fin = winter_medals[winter_medals['Team'] == 'Finland']
winter_usa = winter_medals[winter_medals['Team'] == 'United States']

winter_fin_medals = winter_fin.dropna(subset=['Medal']) 
winter_fin_year = winter_fin_medals.groupby('Year')
winter_fin_count = winter_fin_year.count()
sns.lmplot(x='Year', y='Medal',data=winter_fin_count.reset_index(), size=10)


# In[ ]:


groupby_year = summer_medals.groupby('Year')
medal_count = groupby_year.count()
medal_count.head()


# In[ ]:


#count of total number of medals won total over the years in Summer Olympics
sns.lmplot(x='Year', y='Medal',data=medal_count.reset_index(), size=10)


# In[ ]:


#China
df_china = summer_medals[summer_medals['Team'] == 'China']
df_china_medals = df_china.dropna(subset=['Medal']) 
china_groupby_year = df_china_medals.groupby('Year')
china_medal_count = china_groupby_year.count()
sns.lmplot(x='Year', y='Medal',data=china_medal_count.reset_index(), size=10)


# In[ ]:


"""
-What about the use of sport enhancing drugs in the early 50s? Will we see a sharp rise in the
medal count for developed countries such as china, usa, and russia?

-Doesn't seem to look like that happened. Comforting to the sport lovers out there
"""


# In[ ]:


summer_by_team = summer_medals.groupby('Team')
summer_count = summer_by_team.count()
summer_count


# In[ ]:


plt.figure(figsize = (20,10))

df_france = summer_medals[summer_medals['Team'] == 'France']
df_france_medals = df_france.dropna(subset=['Medal']) 
france_groupby_year = df_france_medals.groupby('Year')
france_medal_count = france_groupby_year.count()
france = france_medal_count.reset_index()
plt.plot(france['Year'], france['Medal'])


df_italy = summer_medals[summer_medals['Team'] == 'Italy']
df_italy_medals = df_italy.dropna(subset=['Medal']) 
italy_groupby_year = df_italy_medals.groupby('Year')
italy_medal_count = italy_groupby_year.count()
italy = italy_medal_count.reset_index()
plt.plot(italy['Year'], italy['Medal'])

df_hungary = summer_medals[summer_medals['Team'] == 'Hungary']
df_hungary_medals = df_hungary.dropna(subset=['Medal']) 
hungary_groupby_year = df_hungary_medals.groupby('Year')
hungary_medal_count = hungary_groupby_year.count()
hungary = hungary_medal_count.reset_index()
plt.plot(hungary['Year'], hungary['Medal'])

df_sweden = summer_medals[summer_medals['Team'] == 'Sweden']
df_sweden_medals = df_sweden.dropna(subset=['Medal']) 
sweden_groupby_year = df_sweden_medals.groupby('Year')
sweden_medal_count = sweden_groupby_year.count()
sweden = sweden_medal_count.reset_index()
plt.plot(sweden['Year'], sweden['Medal'])

df_uk = summer_medals[summer_medals['Team'] == 'United Kingdom']
df_uk_medals = df_uk.dropna(subset=['Medal']) 
uk_groupby_year = df_uk_medals.groupby('Year')
uk_medal_count = uk_groupby_year.count()
uk = uk_medal_count.reset_index()
plt.plot(uk['Year'], uk['Medal'])

df_russia = summer_medals[summer_medals['Team'] == 'Russia']
df_russia_medals = df_russia.dropna(subset=['Medal']) 
russia_groupby_year = df_russia_medals.groupby('Year')
russia_medal_count = russia_groupby_year.count()
russia = russia_medal_count.reset_index()
plt.plot(russia['Year'], russia['Medal'])

china = china_medal_count.reset_index()
usa = usa_medal_count.reset_index()
finland =fin_medal_count.reset_index()

plt.plot(china['Year'], china['Medal'])
plt.plot(usa['Year'], usa['Medal'])
plt.plot(finland['Year'], finland['Medal'])


# In[ ]:


#looks like height vs weight has the strongest correlation in the years the olympics has continued
#plt.figure(figsize = (20,10))
#sns.heatmap(winter_medals.corr())


# In[ ]:


plt.figure(figsize=(20,10))
sns.heatmap(summer_medals.corr())


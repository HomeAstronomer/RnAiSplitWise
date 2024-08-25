import React, { useEffect } from 'react';
import { View, Text, Button, StyleSheet, BackHandler, Alert, TextInput, TouchableOpacity, KeyboardAvoidingView, Platform, ScrollView, FlatList } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../App'; // Adjust the path as necessary
import { SafeAreaView } from 'react-native-safe-area-context';
import AsyncStorage from '@react-native-async-storage/async-storage';

type HomeScreenProps = NativeStackScreenProps<RootStackParamList, 'Home'>;

const HomeScreen: React.FC<HomeScreenProps> = ({ navigation }) => {
  const [value, onChangeText]=React.useState("")
  const [storedData, updateData] = React.useState<string[]>([]);
  const storeData = async ( value: string[]) => {
    try {
      const jsonValue = JSON.stringify(value);
      await AsyncStorage.setItem('@key', jsonValue);
      const getJson  = await AsyncStorage.getItem('@key');
    if (getJson  !== null) {
      const output:string[]= JSON.parse(getJson) 

      updateData(output)
    }
    } catch (e) {
      // saving error
    }
  };
  const addNewString = (newString: string): string[] => {
    const updatedData = [...storedData, newString];
    return updatedData;
};

  const getMyObject = async () => {
    try {
      const jsonValue  = await AsyncStorage.getItem('@key');
    if (jsonValue  !== null) {
      const output:string[]= JSON.parse(jsonValue) 

      updateData(output)
    }
    } catch(e) {
      // read error
    }
  
    console.log('Done.')
  }
  type ItemProps = {title: string};

  const Item = ({title}: ItemProps) => (
    <View style={styles.item}>
      <Text style={styles.title}>{title}</Text>
    </View>
  );
  return (
    <KeyboardAvoidingView
    style={styles.container}
    behavior={Platform.OS === 'ios' ? 'padding' : 'height'} // Adjust behavior based on the platform
  >
    <ScrollView contentContainerStyle={styles.scrollContainer}>
      <View style={styles.topContainer}>
        <Text style={styles.title}>Groups</Text>
        <Text style={styles.text}>Details Screen</Text>
        <TextInput
          value={value}
          onChangeText={onChangeText}
          keyboardType="email-address"
          style={styles.textInput} // Apply styles here
          placeholder="Enter Group Name"
          placeholderTextColor="#d68e8f" // Light pink placeholder text color
        />
        <TouchableOpacity 
          style={styles.enterGroupButton} 
          onPress={() => {
            storeData(addNewString(value))
          }}
        >
          <Text style={styles.buttonText}>Add item To Async</Text>
        </TouchableOpacity>
        <TouchableOpacity 
          style={styles.enterGroupButton} 
          onPress={() => {
            getMyObject()
          }}
        >
          <Text style={styles.buttonText}>Get New Data</Text>
        </TouchableOpacity>
        
      </View>
    </ScrollView>
    <FlatList
        data={storedData}
        renderItem={({item}) => <Item title={item} />}
        keyExtractor={item => item}
      />

    <TouchableOpacity 
      style={styles.detailScreenButton} 
      onPress={() => navigation.navigate('Details')}
    >
      <Text style={styles.buttonText}>Go to Detail Screen</Text>
    </TouchableOpacity>
  </KeyboardAvoidingView>
);


};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    padding: 16,
  },
  scrollContainer: {
    flexGrow: 1,
    justifyContent: 'space-between',
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
    color: '#ff69b4', // Hot pink for the title
  },
  text: {
    fontSize: 24,
    marginBottom: 20,
    color: '#ff69b4', // Hot pink for the text
  },
  topContainer: {
    flex: 1,
    justifyContent: 'center',
    paddingBottom: 20,
  },
  textInput: {
    height: 50,
    borderColor: '#ff69b4', // Pink border color
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 10,
    fontSize: 16,
    marginBottom: 20,
    color: 'black',
  },
  detailScreenButton: {
    backgroundColor: '#ff69b4', // Hot pink for a vibrant look
    paddingVertical: 12,
    paddingHorizontal: 32,
    borderRadius: 30,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 4,
    elevation: 3,
  },
  enterGroupButton: {
    backgroundColor: '#ffb6c1', // Light pink for a softer look
    paddingVertical: 12,
    paddingHorizontal: 32,
    borderRadius: 30,
    alignItems: 'center',
    justifyContent: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 4,
    elevation: 3,
  },
  buttonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
  },
  item: {
    backgroundColor: '#f9c2ff',
    padding: 10,
    marginVertical: 8,
    marginHorizontal: 16,
  },
});


export default HomeScreen;

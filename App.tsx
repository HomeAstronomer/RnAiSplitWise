
import React, { useEffect } from 'react';
import { NavigationContainer, useNavigation } from '@react-navigation/native';
import { createNativeStackNavigator, NativeStackNavigationProp } from '@react-navigation/native-stack';
import DetailsScreen from './rnScreens/DetailsScreen';
import { Alert, BackHandler } from 'react-native';
import HomeScreen from './rnScreens/HomeScreen';
import { SafeAreaView } from 'react-native-safe-area-context';

// Define your RootStackParamList to type-check navigation
export type RootStackParamList = {
  Home: undefined;
  Details: undefined;
};

const Stack = createNativeStackNavigator<RootStackParamList>();

function App(): React.JSX.Element {

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} 
        options={{ headerShown: false }}/>
        <Stack.Screen name="Details" component={DetailsScreen} 
        options={{ headerShown: false}}/>
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;
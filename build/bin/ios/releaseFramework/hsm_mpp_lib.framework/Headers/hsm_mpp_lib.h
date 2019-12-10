#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class Hsm_mpp_libState, Hsm_mpp_libTransitionKind, Hsm_mpp_libAction, Hsm_mpp_libStateMachine, Hsm_mpp_libEvent, Hsm_mpp_libKotlinArray, Hsm_mpp_libParallel, Hsm_mpp_libSub, Hsm_mpp_libKotlinEnum;

@protocol Hsm_mpp_libGuard, Hsm_mpp_libILogger, Hsm_mpp_libEventHandler, Hsm_mpp_libKotlinComparable, Hsm_mpp_libKotlinIterator;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wnullability"

@interface KotlinBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface KotlinBase (KotlinBaseCopying) <NSCopying>
@end;

__attribute__((objc_runtime_name("KotlinMutableSet")))
__attribute__((swift_name("KotlinMutableSet")))
@interface Hsm_mpp_libMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((objc_runtime_name("KotlinMutableDictionary")))
__attribute__((swift_name("KotlinMutableDictionary")))
@interface Hsm_mpp_libMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((objc_runtime_name("KotlinNumber")))
__attribute__((swift_name("KotlinNumber")))
@interface Hsm_mpp_libNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end;

__attribute__((objc_runtime_name("KotlinByte")))
__attribute__((swift_name("KotlinByte")))
@interface Hsm_mpp_libByte : Hsm_mpp_libNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((objc_runtime_name("KotlinUByte")))
__attribute__((swift_name("KotlinUByte")))
@interface Hsm_mpp_libUByte : Hsm_mpp_libNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((objc_runtime_name("KotlinShort")))
__attribute__((swift_name("KotlinShort")))
@interface Hsm_mpp_libShort : Hsm_mpp_libNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((objc_runtime_name("KotlinUShort")))
__attribute__((swift_name("KotlinUShort")))
@interface Hsm_mpp_libUShort : Hsm_mpp_libNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((objc_runtime_name("KotlinInt")))
__attribute__((swift_name("KotlinInt")))
@interface Hsm_mpp_libInt : Hsm_mpp_libNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((objc_runtime_name("KotlinUInt")))
__attribute__((swift_name("KotlinUInt")))
@interface Hsm_mpp_libUInt : Hsm_mpp_libNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((objc_runtime_name("KotlinLong")))
__attribute__((swift_name("KotlinLong")))
@interface Hsm_mpp_libLong : Hsm_mpp_libNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((objc_runtime_name("KotlinULong")))
__attribute__((swift_name("KotlinULong")))
@interface Hsm_mpp_libULong : Hsm_mpp_libNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((objc_runtime_name("KotlinFloat")))
__attribute__((swift_name("KotlinFloat")))
@interface Hsm_mpp_libFloat : Hsm_mpp_libNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((objc_runtime_name("KotlinDouble")))
__attribute__((swift_name("KotlinDouble")))
@interface Hsm_mpp_libDouble : Hsm_mpp_libNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((objc_runtime_name("KotlinBoolean")))
__attribute__((swift_name("KotlinBoolean")))
@interface Hsm_mpp_libBoolean : Hsm_mpp_libNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((swift_name("Action")))
@interface Hsm_mpp_libAction : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)run __attribute__((swift_name("run()")));
- (void)setNextStateState:(Hsm_mpp_libState * _Nullable)state __attribute__((swift_name("setNextState(state:)")));
- (void)setPayloadPayload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("setPayload(payload:)")));
- (void)setPreviousStateState:(Hsm_mpp_libState * _Nullable)state __attribute__((swift_name("setPreviousState(state:)")));
@property Hsm_mpp_libState * _Nullable mNextState __attribute__((swift_name("mNextState")));
@property NSDictionary<id, id> * _Nullable mPayload __attribute__((swift_name("mPayload")));
@property Hsm_mpp_libState * _Nullable mPreviousState __attribute__((swift_name("mPreviousState")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ConcurrentLinkedQueue")))
@interface Hsm_mpp_libConcurrentLinkedQueue : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (BOOL)addElement:(id _Nullable)element __attribute__((swift_name("add(element:)")));
- (id _Nullable)peek __attribute__((swift_name("peek()")));
- (id _Nullable)poll __attribute__((swift_name("poll()")));
@property NSMutableArray<id> *queue __attribute__((swift_name("queue")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Event")))
@interface Hsm_mpp_libEvent : KotlinBase
- (instancetype)initWithName:(NSString * _Nullable)name payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("init(name:payload:)"))) __attribute__((objc_designated_initializer));
@property (readonly) NSString * _Nullable name __attribute__((swift_name("name")));
@property (readonly) NSDictionary<id, id> * _Nullable payload __attribute__((swift_name("payload")));
@end;

__attribute__((swift_name("EventHandler")))
@protocol Hsm_mpp_libEventHandler
@required
- (void)handleEventEvent:(NSString * _Nullable)event __attribute__((swift_name("handleEvent(event:)")));
- (void)handleEventEvent:(NSString * _Nullable)event payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("handleEvent(event:payload:)")));
@end;

__attribute__((swift_name("Guard")))
@protocol Hsm_mpp_libGuard
@required
- (BOOL)evaluatePayload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("evaluate(payload:)")));
@end;

__attribute__((swift_name("ILogger")))
@protocol Hsm_mpp_libILogger
@required
- (void)debugMessage:(NSString * _Nullable)message __attribute__((swift_name("debug(message:)")));
@end;

__attribute__((swift_name("State")))
@interface Hsm_mpp_libState : KotlinBase
- (instancetype)initWithId:(NSString *)id __attribute__((swift_name("init(id:)"))) __attribute__((objc_designated_initializer));
- (Hsm_mpp_libState *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind __attribute__((swift_name("addHandler(eventName:target:kind:)")));
- (Hsm_mpp_libState *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind action:(Hsm_mpp_libAction *)action __attribute__((swift_name("addHandler(eventName:target:kind:action:)")));
- (Hsm_mpp_libState *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind action:(Hsm_mpp_libAction *)action guard:(id<Hsm_mpp_libGuard>)guard __attribute__((swift_name("addHandler(eventName:target:kind:action:guard:)")));
- (Hsm_mpp_libState *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind guard:(id<Hsm_mpp_libGuard>)guard __attribute__((swift_name("addHandler(eventName:target:kind:guard:)")));
- (void)addParentStateMachine:(Hsm_mpp_libStateMachine *)stateMachine __attribute__((swift_name("addParent(stateMachine:)")));
- (void)enterPrev:(Hsm_mpp_libState * _Nullable)prev next:(Hsm_mpp_libState * _Nullable)next payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("enter(prev:next:payload:)")));
- (void)exitPrev:(Hsm_mpp_libState * _Nullable)prev next:(Hsm_mpp_libState * _Nullable)next payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("exit(prev:next:payload:)")));
- (Hsm_mpp_libStateMachine * _Nullable)getOwner __attribute__((swift_name("getOwner()")));
- (Hsm_mpp_libState *)getThis __attribute__((swift_name("getThis()")));
- (BOOL)handleWithOverrideEvent:(Hsm_mpp_libEvent *)event __attribute__((swift_name("handleWithOverride(event:)")));
- (Hsm_mpp_libState *)onEnterOnEnterAction:(Hsm_mpp_libAction *)onEnterAction __attribute__((swift_name("onEnter(onEnterAction:)")));
- (Hsm_mpp_libState *)onExitOnExitAction:(Hsm_mpp_libAction *)onExitAction __attribute__((swift_name("onExit(onExitAction:)")));
- (void)setLoggerLog:(id<Hsm_mpp_libILogger>)log __attribute__((swift_name("setLogger(log:)")));
- (void)setOwnerOwnerMachine:(Hsm_mpp_libStateMachine *)ownerMachine __attribute__((swift_name("setOwner(ownerMachine:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<Hsm_mpp_libState *> *allActiveStates __attribute__((swift_name("allActiveStates")));
@property (readonly) id descendantStates __attribute__((swift_name("descendantStates")));
@property (readonly) id<Hsm_mpp_libEventHandler> eventHandler __attribute__((swift_name("eventHandler")));
@property NSString *id __attribute__((swift_name("id")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Parallel")))
@interface Hsm_mpp_libParallel : Hsm_mpp_libState
- (instancetype)initWithId:(NSString * _Nullable)id stateMachines:(Hsm_mpp_libKotlinArray *)stateMachines __attribute__((swift_name("init(id:stateMachines:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithId:(NSString *)id __attribute__((swift_name("init(id:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (Hsm_mpp_libParallel *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind __attribute__((swift_name("addHandler(eventName:target:kind:)")));
- (Hsm_mpp_libParallel *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind action:(Hsm_mpp_libAction *)action __attribute__((swift_name("addHandler(eventName:target:kind:action:)")));
- (Hsm_mpp_libParallel *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind action:(Hsm_mpp_libAction *)action guard:(id<Hsm_mpp_libGuard>)guard __attribute__((swift_name("addHandler(eventName:target:kind:action:guard:)")));
- (Hsm_mpp_libParallel *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind guard:(id<Hsm_mpp_libGuard>)guard __attribute__((swift_name("addHandler(eventName:target:kind:guard:)")));
- (void)addParentStateMachine:(Hsm_mpp_libStateMachine *)stateMachine __attribute__((swift_name("addParent(stateMachine:)")));
- (void)enterPrev:(Hsm_mpp_libState * _Nullable)prev next:(Hsm_mpp_libState * _Nullable)next payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("enter(prev:next:payload:)")));
- (void)exitPrev:(Hsm_mpp_libState * _Nullable)prev next:(Hsm_mpp_libState * _Nullable)next payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("exit(prev:next:payload:)")));
- (Hsm_mpp_libParallel *)getThis __attribute__((swift_name("getThis()")));
- (BOOL)handleWithOverrideEvent:(Hsm_mpp_libEvent *)event __attribute__((swift_name("handleWithOverride(event:)")));
- (Hsm_mpp_libParallel *)onEnterOnEnterAction:(Hsm_mpp_libAction *)onEnterAction __attribute__((swift_name("onEnter(onEnterAction:)")));
- (Hsm_mpp_libParallel *)onExitOnExitAction:(Hsm_mpp_libAction *)onExitAction __attribute__((swift_name("onExit(onExitAction:)")));
- (void)setStateMachineListStateMachineList:(NSArray<Hsm_mpp_libStateMachine *> * _Nullable)stateMachineList __attribute__((swift_name("setStateMachineList(stateMachineList:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<Hsm_mpp_libState *> *allActiveStates __attribute__((swift_name("allActiveStates")));
@property (readonly) id descendantStates __attribute__((swift_name("descendantStates")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("State.Companion")))
@interface Hsm_mpp_libStateCompanion : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
- (Hsm_mpp_libState *)createInstanceId:(NSString *)id __attribute__((swift_name("createInstance(id:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("StateMachine")))
@interface Hsm_mpp_libStateMachine : KotlinBase <Hsm_mpp_libEventHandler>
- (instancetype)initWithName:(NSString *)name initialState:(Hsm_mpp_libState *)initialState states:(Hsm_mpp_libKotlinArray *)states __attribute__((swift_name("init(name:initialState:states:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithInitialState:(Hsm_mpp_libState * _Nullable)initialState states:(Hsm_mpp_libKotlinArray *)states __attribute__((swift_name("init(initialState:states:)"))) __attribute__((objc_designated_initializer));
- (void)handleEventEvent:(NSString * _Nullable)event __attribute__((swift_name("handleEvent(event:)")));
- (void)handleEventEvent:(NSString * _Nullable)eventName payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("handleEvent(event:payload:)")));
- (void)doInit __attribute__((swift_name("doInit()")));
- (void)doInitPayload:(NSDictionary<NSString *, id> * _Nullable)payload __attribute__((swift_name("doInit(payload:)")));
- (void)setLoggerLog:(id<Hsm_mpp_libILogger>)log __attribute__((swift_name("setLogger(log:)")));
- (void)teardown __attribute__((swift_name("teardown()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<Hsm_mpp_libState *> *allActiveStates __attribute__((swift_name("allActiveStates")));
@property NSMutableArray<Hsm_mpp_libState *> *descendantStates __attribute__((swift_name("descendantStates")));
@property (readonly) NSString *pathString __attribute__((swift_name("pathString")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Sub")))
@interface Hsm_mpp_libSub : Hsm_mpp_libState
- (instancetype)initWithId:(NSString * _Nullable)id subMachine:(Hsm_mpp_libStateMachine *)subMachine __attribute__((swift_name("init(id:subMachine:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithId:(NSString * _Nullable)id initialState:(Hsm_mpp_libState * _Nullable)initialState states:(Hsm_mpp_libKotlinArray *)states __attribute__((swift_name("init(id:initialState:states:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithId:(NSString *)id __attribute__((swift_name("init(id:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
- (Hsm_mpp_libSub *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind __attribute__((swift_name("addHandler(eventName:target:kind:)")));
- (Hsm_mpp_libSub *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind action:(Hsm_mpp_libAction *)action __attribute__((swift_name("addHandler(eventName:target:kind:action:)")));
- (Hsm_mpp_libSub *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind action:(Hsm_mpp_libAction *)action guard:(id<Hsm_mpp_libGuard>)guard __attribute__((swift_name("addHandler(eventName:target:kind:action:guard:)")));
- (Hsm_mpp_libSub *)addHandlerEventName:(NSString *)eventName target:(Hsm_mpp_libState *)target kind:(Hsm_mpp_libTransitionKind *)kind guard:(id<Hsm_mpp_libGuard>)guard __attribute__((swift_name("addHandler(eventName:target:kind:guard:)")));
- (void)addParentStateMachine:(Hsm_mpp_libStateMachine *)stateMachine __attribute__((swift_name("addParent(stateMachine:)")));
- (void)enterPrev:(Hsm_mpp_libState * _Nullable)prev next:(Hsm_mpp_libState * _Nullable)next payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("enter(prev:next:payload:)")));
- (void)exitPrev:(Hsm_mpp_libState * _Nullable)prev next:(Hsm_mpp_libState * _Nullable)next payload:(NSDictionary<id, id> * _Nullable)payload __attribute__((swift_name("exit(prev:next:payload:)")));
- (Hsm_mpp_libSub *)getThis __attribute__((swift_name("getThis()")));
- (BOOL)handleWithOverrideEvent:(Hsm_mpp_libEvent *)event __attribute__((swift_name("handleWithOverride(event:)")));
- (Hsm_mpp_libSub *)onEnterOnEnterAction:(Hsm_mpp_libAction *)onEnterAction __attribute__((swift_name("onEnter(onEnterAction:)")));
- (Hsm_mpp_libSub *)onExitOnExitAction:(Hsm_mpp_libAction *)onExitAction __attribute__((swift_name("onExit(onExitAction:)")));
- (void)setOwnerOwnerMachine:(Hsm_mpp_libStateMachine *)ownerMachine __attribute__((swift_name("setOwner(ownerMachine:)")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSArray<Hsm_mpp_libState *> *allActiveStates __attribute__((swift_name("allActiveStates")));
@property (readonly) id descendantStates __attribute__((swift_name("descendantStates")));
@end;

__attribute__((swift_name("KotlinComparable")))
@protocol Hsm_mpp_libKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((swift_name("KotlinEnum")))
@interface Hsm_mpp_libKotlinEnum : KotlinBase <Hsm_mpp_libKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
- (int32_t)compareToOther:(Hsm_mpp_libKotlinEnum *)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("TransitionKind")))
@interface Hsm_mpp_libTransitionKind : Hsm_mpp_libKotlinEnum
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) Hsm_mpp_libTransitionKind *external __attribute__((swift_name("external")));
@property (class, readonly) Hsm_mpp_libTransitionKind *local __attribute__((swift_name("local")));
@property (class, readonly) Hsm_mpp_libTransitionKind *internal __attribute__((swift_name("internal")));
- (int32_t)compareToOther:(Hsm_mpp_libTransitionKind *)other __attribute__((swift_name("compareTo(other:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Platform")))
@interface Hsm_mpp_libPlatform : KotlinBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)platform __attribute__((swift_name("init()")));
- (NSString *)name __attribute__((swift_name("name()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Sample")))
@interface Hsm_mpp_libSample : KotlinBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (int32_t)checkMe __attribute__((swift_name("checkMe()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SampleKt")))
@interface Hsm_mpp_libSampleKt : KotlinBase
+ (NSString *)hello __attribute__((swift_name("hello()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface Hsm_mpp_libKotlinArray : KotlinBase
+ (instancetype)arrayWithSize:(int32_t)size init:(id _Nullable (^)(Hsm_mpp_libInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (id _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<Hsm_mpp_libKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(id _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol Hsm_mpp_libKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end;

#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
